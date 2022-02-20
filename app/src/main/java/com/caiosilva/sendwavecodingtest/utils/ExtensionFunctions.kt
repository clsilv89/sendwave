package com.caiosilva.sendwavecodingtest.utils

import android.content.Context
import android.widget.EditText
import androidx.annotation.StringRes

fun Int.isLowerThan(comparable: Int): Boolean {
    return this < comparable
}

fun EditText.showError( context: Context, @StringRes errorString: Int) {
    error = context.getString(errorString)
    requestFocus()
}