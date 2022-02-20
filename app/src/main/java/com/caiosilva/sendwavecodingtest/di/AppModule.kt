package com.caiosilva.sendwavecodingtest.di

import com.caiosilva.sendwavecodingtest.BuildConfig
import com.caiosilva.sendwavecodingtest.data.api.Api
import com.caiosilva.sendwavecodingtest.data.network.NetWorkHelp
import com.caiosilva.sendwavecodingtest.data.network.RestConfig
import com.caiosilva.sendwavecodingtest.data.repository.Repository
import com.caiosilva.sendwavecodingtest.usecases.GetConversionUseCaseImpl
import com.caiosilva.sendwavecodingtest.usecases.IGetConversionUseCase
import com.caiosilva.sendwavecodingtest.viewmodel.MainActivityViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModule {
    val module = module {
        viewModel {
            MainActivityViewModel(
                getConversionUseCaseImpl = get()
            )
        }

        single(named(DatasourceDITag.BASE_URL)) { BuildConfig.BASE_URL }

        single<IGetConversionUseCase> {
            GetConversionUseCaseImpl(get() as Repository)
        }

        factory {
            NetWorkHelp
        }

        single {
            Repository(
                get(), get()
            )
        }

        factory {
            val httpLoggingInterceptorLevel = HttpLoggingInterceptor.Level.BODY
            HttpLoggingInterceptor().apply {
                level = httpLoggingInterceptorLevel
            }
        }

        factory {
            OkHttpClient
                .Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single<Api> {
            RestConfig.service<Api>(BuildConfig.BASE_URL)
        }
    }
}
