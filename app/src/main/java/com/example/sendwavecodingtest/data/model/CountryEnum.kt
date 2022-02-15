package com.example.sendwavecodingtest.data.model

enum class CountryEnum(
    val countryName: String,
    val countryCode: String,
    val countryPhonePrefix: String
) {
    KENYA("Kenya", "KES", "+254"),
    NIGERIA("Nigeria", "NGN", "+234"),
    TANZANIA("Tanzania", "TZS", "+255"),
    UGANDA("Uganda", "UGX", "+256");

    companion object {
        fun get(value: String) = values().first {
            it.countryName.contains(value)
        }
    }
}