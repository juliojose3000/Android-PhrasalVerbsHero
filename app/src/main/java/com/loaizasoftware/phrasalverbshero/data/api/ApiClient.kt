package com.loaizasoftware.phrasalverbshero.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.loaizasoftware.phrasalverbshero.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

//import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val BASE_URL = BuildConfig.BASE_URL
    lateinit var apiService: ApiService

    fun createRetrofit(context: Context) {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Allows Moshi to handle Kotlin classes properly
            .build()

        // Create and configure the logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response lines and their respective headers and bodies (if present)
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
            .addInterceptor(ChuckerInterceptor(context)) // Add Chucker interceptor
            .addInterceptor(loggingInterceptor)
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Use Moshi converter to deserialize JSON responses
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Allows Retrofit works with RxJava
            .build()
            .create(ApiService::class.java)

    }

}