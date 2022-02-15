package com.example.sendwavecodingtest.data.api

import com.example.sendwavecodingtest.data.dto.ExchangeResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
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
