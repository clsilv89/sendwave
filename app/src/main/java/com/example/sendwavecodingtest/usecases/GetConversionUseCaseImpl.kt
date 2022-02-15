package com.example.sendwavecodingtest.usecases

import com.example.sendwavecodingtest.data.dto.toModel
import com.example.sendwavecodingtest.data.model.ExchangeResponseModel
import com.example.sendwavecodingtest.data.network.ResultWrapper
import com.example.sendwavecodingtest.data.repository.Repository

class GetConversionUseCaseImpl(
    private val repository: Repository
): IGetConversionUseCase {
    override suspend fun invoke(): ResultWrapper<ExchangeResponseModel> {
        return repository.getConversion()
    }
}