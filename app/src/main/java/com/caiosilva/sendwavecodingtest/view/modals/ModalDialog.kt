package com.caiosilva.sendwavecodingtest.view.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.caiosilva.sendwavecodingtest.R
import com.caiosilva.sendwavecodingtest.databinding.FragmentModalBinding
import com.caiosilva.sendwavecodingtest.utils.OnClickListener
import com.caiosilva.sendwavecodingtest.view.baseviews.BaseDialog

class ModalDialog : BaseDialog() {

    private lateinit var binding: FragmentModalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_modal,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.isCancelable = false

        arguments?.let { bundleLet ->
            binding.textviewAmmount.text = bundleLet.getString(AMMOUNT)
            binding.textviewRecipient.text = bundleLet.getString(RECIPIENT)
            binding.textviewPhone.text = bundleLet.getString(PHONE)
            binding.buttonConfirm.setOnClickListener {
                (bundleLet.getSerializable(BOTAO_CONFIRM_LISTENER) as OnClickListener).onClick(it)
                dismissAllowingStateLoss()
            }
            binding.buttonCancel.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    companion object {
        private const val AMMOUNT = "AMMOUNT"
        private const val RECIPIENT = "RECIPIENT"
        private const val PHONE = "PHONE"
        private const val BOTAO_CONFIRM_LISTENER = "BOTAO_CONFIRM_LISTENER"

        @JvmStatic
        fun newInstace(
            ammount: String, recipient: String, phone: String,
            confirmListener: OnClickListener
        ) = ModalDialog().apply {
            arguments = Bundle().apply {
                putString(AMMOUNT, ammount)
                putString(RECIPIENT, recipient)
                putString(PHONE, phone)
                putSerializable(BOTAO_CONFIRM_LISTENER, confirmListener)
            }
        }
    }
}