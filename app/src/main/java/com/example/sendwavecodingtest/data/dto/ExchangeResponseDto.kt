package com.example.sendwavecodingtest.data.dto

import com.example.sendwavecodingtest.data.model.ExchangeResponseModel

data class ExchangeResponseDto(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: ExchangeRateDto
)

internal fun ExchangeResponseDto.toModel(): ExchangeResponseModel {
    return ExchangeResponseModel(
        this.disclaimer,
        this.license,
        this.timestamp,
        this.base,
        this.rates.toModel()
    )
}
