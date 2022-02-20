package com.caiosilva.sendwavecodingtest.data.network

import com.caiosilva.sendwavecodingtest.data.dto.ExchangeRateDto
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

object RestConfig {
    private const val TIME_OUT = 20L

    internal inline fun <reified S> service(url: String): S {
        return Retrofit
            .Builder()
            .baseUrl(url)
            .extras()
            .client()
            .build()
            .create(S::class.java)
    }

    private fun Retrofit.Builder.client(): Retrofit.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .timeout()
            .interceptor()
            .build()
        return client(okHttpClient)
    }

    private fun Retrofit.Builder.extras(): Retrofit.Builder {
        val listType: Type = object : TypeToken<MutableList<ExchangeRateDto>>() {}.type
        val gsonDeserializer = GsonBuilder()
            .registerTypeAdapter(
                listType,
                ConverterFactory()
            )
            .create()

        val gsonConverter = GsonConverterFactory.create(gsonDeserializer)
        val rxAdapter = RxJava2CallAdapterFactory.create()

        return addConverterFactory(gsonConverter)
            .addCallAdapterFactory(rxAdapter)
    }

    private fun OkHttpClient.Builder.timeout(): OkHttpClient.Builder {
        return readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
    }

    private fun OkHttpClient.Builder.interceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        addInterceptor(logging)
        return this
    }
}