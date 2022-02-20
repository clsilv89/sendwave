package com.caiosilva.sendwavecodingtest.data.api

import com.caiosilva.sendwavecodingtest.data.dto.ExchangeResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(LATEST_JSON)
    suspend fun getConversion(
        @Query("app_id") appId: String,
    ): ExchangeResponseDto

    companion object {
        private const val LATEST_JSON = "latest.json"
    }
}
