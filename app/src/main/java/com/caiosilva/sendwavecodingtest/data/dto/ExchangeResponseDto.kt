package com.caiosilva.sendwavecodingtest.data.dto

import com.caiosilva.sendwavecodingtest.data.model.ExchangeResponseModel

data class ExchangeResponseDto(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: List<ExchangeRateDto>
)

internal fun ExchangeResponseDto.toModel(): ExchangeResponseModel {
    return ExchangeResponseModel(
        this.disclaimer,
        this.license,
        this.timestamp,
        this.base,
        this.rates.map { it.toModel() }
    )
}
