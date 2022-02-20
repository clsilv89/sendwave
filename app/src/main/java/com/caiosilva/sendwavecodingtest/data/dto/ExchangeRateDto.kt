package com.caiosilva.sendwavecodingtest.data.dto

import com.caiosilva.sendwavecodingtest.data.model.ExchangeRateModel

data class ExchangeRateDto(
    val currency: String?,
    val rate: Double?
)

internal fun ExchangeRateDto.toModel(): ExchangeRateModel {
    return ExchangeRateModel(
        this.currency.orEmpty(),
        this.rate ?: 0.0
    )
}
