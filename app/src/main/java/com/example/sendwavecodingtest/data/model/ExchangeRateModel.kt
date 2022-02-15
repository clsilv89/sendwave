package com.example.sendwavecodingtest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeRateModel(
    val exchangeRates: List<ExchangeRate>,
    val currency: String,
    val rate: Double
) : Parcelable
