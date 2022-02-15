package com.example.sendwavecodingtest.data.dto

import com.example.sendwavecodingtest.data.model.ExchangeRate
import com.example.sendwavecodingtest.data.model.ExchangeRateModel

data class ExchangeRateDto(
    val exchangeRates: List<ExchangeRate>,
    val currency: String?,
    val rate: Double?
)

internal fun ExchangeRateDto.toModel(): ExchangeRateModel {
    return ExchangeRateModel(
        this.exchangeRates ?: emptyList(),
        this.currency.orEmpty(),
        this.rate ?: 0.0
    )
}
