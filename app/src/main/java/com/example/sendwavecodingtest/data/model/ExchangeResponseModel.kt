package com.example.sendwavecodingtest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ExchangeResponseModel (
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rateModels: ExchangeRateModel
    ) : Parcelable
