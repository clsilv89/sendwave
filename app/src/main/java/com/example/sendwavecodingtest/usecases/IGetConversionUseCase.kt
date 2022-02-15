package com.example.sendwavecodingtest.usecases

import com.example.sendwavecodingtest.data.model.ExchangeResponseModel
import com.example.sendwavecodingtest.data.network.ResultWrapper

interface IGetConversionUseCase {
    suspend fun invoke(): ResultWrapper<ExchangeResponseModel>
}