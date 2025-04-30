package com.argel.weathertraker.framework.api

import com.argel.weathertraker.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiProvider @Inject constructor(authorizationInterceptor: AuthorizationInterceptor) {

    private var retrofit: Retrofit

    init {

        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            httpClientBuilder.addInterceptor(logging)
        }

        httpClientBuilder.addInterceptor(authorizationInterceptor)

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(httpClientBuilder.build())
            .build()

    }

    fun <S> getEndpoint(serviceClass: Class<S>): S = retrofit.create(serviceClass)

}