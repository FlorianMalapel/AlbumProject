package com.flo.albumproject.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {

    val BASE_URL = "https://static.leboncoin.fr/"

    var technicalTestApi: TechnicalTestApi

    /**
     * Ok, it's an Experimental Api, BUT the documentation says "try it only in toy projects",
     * as it's pretty handy, and, as we are not on a production project,
     * I think it's a great place to use it :)
     */
//    @OptIn(ExperimentalStdlibApi::class)
    private val moshi by lazy {
        val moshiBuilder = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
//            .addAdapter(JsonMoshiAdapter())
        moshiBuilder.build()
    }

    private val loggingInterceptor by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    init {
        getRetrofit().let { retrofit ->
            this.technicalTestApi = retrofit.create(TechnicalTestApi::class.java)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}