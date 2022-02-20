package com.caiosilva.sendwavecodingtest.data.repository

import com.caiosilva.sendwavecodingtest.BuildConfig
import com.caiosilva.sendwavecodingtest.data.api.Api
import com.caiosilva.sendwavecodingtest.data.dto.toModel
import com.caiosilva.sendwavecodingtest.data.model.ExchangeResponseModel
import com.caiosilva.sendwavecodingtest.data.network.NetWorkHelp
import com.caiosilva.sendwavecodingtest.data.network.ResultWrapper
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
