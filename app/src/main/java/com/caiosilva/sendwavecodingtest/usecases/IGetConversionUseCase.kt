package com.caiosilva.sendwavecodingtest.usecases

import com.caiosilva.sendwavecodingtest.data.model.ExchangeResponseModel
import com.caiosilva.sendwavecodingtest.data.network.ResultWrapper

interface IGetConversionUseCase {
    suspend fun invoke(): ResultWrapper<ExchangeResponseModel>
}