package com.example.sendwavecodingtest.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.sendwavecodingtest.databinding.ActivityMainBinding
import com.example.sendwavecodingtest.utils.SimpleTextWatcher
import com.example.sendwavecodingtest.viewmodel.MainActivityViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
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
                viewModel.setAmmount(it)
            }
        )
        binding.textinputEditPhone.apply {
            addTextChangedListener(
                SimpleTextWatcher {
                    viewModel.setPhoneNumber(it)
                }
            )
        }
        binding.buttonGetConversion.setOnClickListener {
            viewModel.getConversion()
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
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }
}
