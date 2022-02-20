package com.caiosilva.sendwavecodingtest.view.activities

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caiosilva.sendwavecodingtest.R
import com.caiosilva.sendwavecodingtest.databinding.ActivityMainBinding
import com.caiosilva.sendwavecodingtest.utils.OnClickListener
import com.caiosilva.sendwavecodingtest.utils.SimpleTextWatcher
import com.caiosilva.sendwavecodingtest.utils.isLowerThan
import com.caiosilva.sendwavecodingtest.utils.showError
import com.caiosilva.sendwavecodingtest.view.modals.ModalDialog
import com.caiosilva.sendwavecodingtest.viewmodel.MainActivityViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
    }

    private fun initView() {
        binding.textInputLayoutFirstName.apply {
            post {
                this.requestFocus()
            }
        }
        binding.textInputEditFirstName.addTextChangedListener(
            SimpleTextWatcher {
                viewModel.setFirstName(it)
            }
        )
        binding.textInputEditLastName.addTextChangedListener(
            SimpleTextWatcher {
                viewModel.setLastName(it)
            }
        )
        binding.textinputEditAmmount.addTextChangedListener(
            SimpleTextWatcher {
                if (it.isNotBlank()) {
                    viewModel.setAmmount(it.toLong())
                } else {
                    viewModel.setAmmount(0L)
                }
            }
        )
        binding.textinputEditPhone.addTextChangedListener(
            SimpleTextWatcher {
                viewModel.setPhoneNumber(it)
            }
        )
        binding.buttonSendMoney.setOnClickListener {
            validateInputs()
        }
        binding.spinnerCountry.apply {
            adapter =
                ArrayAdapter<Any?>(
                    this@MainActivity,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    viewModel.getAvailableCoutries()
                )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setSelectedCountry(position)
                    binding.textinputEditPhone.setText("")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.maxPhoneLength.observe(this) {
            binding.textinputEditPhone.apply {
                filters = arrayOf<InputFilter>(LengthFilter(it))
            }
        }
        viewModel.valueToTransfer.observe(this) {
            viewModel.setupRequest()
        }
        viewModel.error.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    "An error has ocurred in loading the data",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        viewModel.sendMoneyRequest.observe(this) {
            val modal: ModalDialog = ModalDialog.newInstace(
                it.value,
                it.recipient,
                it.phone,
                object : OnClickListener {
                    override fun onClick(view: View?) {
                        eraseAllFields()
                    }
                }
            )
            if (!modal.isAdded) {
                modal.show(supportFragmentManager, ModalDialog::class.java.simpleName)
            }
        }
    }

    private fun validateInputs() {
        if (binding.textInputEditFirstName.text.isNullOrBlank()) {
            binding.textInputEditFirstName.showError(this, R.string.edit_text_error)
        } else if (binding.textInputEditLastName.text.isNullOrBlank()) {
            binding.textInputEditLastName.showError(this, R.string.edit_text_error)
        } else if (binding.textinputEditPhone.text?.length
                ?.isLowerThan(viewModel.maxPhoneLength.value ?: 0) == true ||
            binding.textinputEditPhone.text.isNullOrBlank()
        ) {
            binding.textinputEditPhone.showError(this, R.string.edit_text_phone_error)
        }
        else if (binding.textinputEditAmmount.text.isNullOrBlank()) {
            binding.textinputEditAmmount.showError(this, R.string.edit_text_error)
        } else {
            viewModel.calculateValuetoSend()
        }
    }

    private fun eraseAllFields() {
        binding.apply {
            textInputEditFirstName.text = null
            textInputEditLastName.text = null
            textinputEditAmmount.text = null
            textinputEditPhone.text = null
            textInputLayoutFirstName.requestFocus()
        }
    }
}
