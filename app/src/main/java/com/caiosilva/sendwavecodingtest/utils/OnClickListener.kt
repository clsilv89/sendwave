package com.caiosilva.sendwavecodingtest.utils

import android.view.View
import java.io.Serializable

interface OnClickListener: Serializable {
    fun onClick(view: View?)
}