package com.caiosilva.sendwavecodingtest.usecases

import com.caiosilva.sendwavecodingtest.data.model.ExchangeResponseModel
import com.caiosilva.sendwavecodingtest.data.network.ResultWrapper
import com.caiosilva.sendwavecodingtest.data.repository.Repository

class GetConversionUseCaseImpl(
    private val repository: Repository
): IGetConversionUseCase {
    override suspend fun invoke(): ResultWrapper<ExchangeResponseModel> {
        return repository.getConversion()
    }
}