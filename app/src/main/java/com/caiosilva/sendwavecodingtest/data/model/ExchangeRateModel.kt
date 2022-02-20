package com.caiosilva.sendwavecodingtest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeRateModel(
    val currency: String,
    val rate: Double
) : Parcelable
