package com.caiosilva.sendwavecodingtest.data.model

data class SendMoneyRequestModel(
    val recipient: String,
    val phone: String,
    val value: String
)