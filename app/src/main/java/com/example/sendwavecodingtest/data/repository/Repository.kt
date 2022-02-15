package com.example.sendwavecodingtest.data.repository

import com.example.sendwavecodingtest.BuildConfig
import com.example.sendwavecodingtest.data.api.Api
import com.example.sendwavecodingtest.data.dto.ExchangeResponseDto
import com.example.sendwavecodingtest.data.dto.toModel
import com.example.sendwavecodingtest.data.model.ExchangeResponseModel
import com.example.sendwavecodingtest.data.network.NetWorkHelp
import com.example.sendwavecodingtest.data.network.ResultWrapper
import kotlinx.coroutines.Dispatchers

class Repository(
    private val api: Api,
    private val networkHelp: NetWorkHelp
) {
    private val dispatcher = Dispatchers.IO
    suspend fun getConversion(): ResultWrapper<ExchangeResponseModel> =
        networkHelp.safeApiCall(dispatcher) {
            api.getConversion(BuildConfig.API_KEY).toModel()
        }
}
