package com.caiosilva.sendwavecodingtest.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(private val callback: (term: String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        callback(s.toString())
    }
}