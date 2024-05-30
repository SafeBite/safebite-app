package com.celvine.deb.esail.bby.api

import android.content.Context
import com.celvine.deb.esail.bby.helper.SessionManager
import com.celvine.deb.esail.bby.helper.TokenInterceptor
import com.google.gson.GsonBuilder
import com.celvine.deb.esail.bby.util.Config
import com.celvine.deb.esail.bby.util.Config.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private lateinit var apiService: ApiService
    private lateinit var sessionManager: SessionManager

    fun initialize(context: Context) {
        sessionManager = SessionManager(context)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val tokenInterceptor = TokenInterceptor(sessionManager, createApiService())

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    val instanceRetrofit: ApiService
        get() = apiService

    private fun createApiService(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

//object ApiConfig {
//    private val client: Retrofit
//        get() {
//            val gson = GsonBuilder()
//                    .setLenient()
//                    .create()
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//            val client: OkHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(loggingInterceptor)
//                    .connectTimeout(40, TimeUnit.SECONDS)
//                    .readTimeout(40, TimeUnit.SECONDS)
//                    .writeTimeout(40, TimeUnit.SECONDS)
//                    .build()
//
//            return Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(client)
//                    .build()
//        }
//
//    val instanceRetrofit: ApiService
//        get() = client.create(ApiService::class.java)
//}