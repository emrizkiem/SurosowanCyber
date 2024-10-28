package com.example.surosowancyber.implement.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request()
                    val requestBuilder = request.newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "")
                        .build()
                    chain.proceed(requestBuilder)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val THUMBNAIL_URL = "https://img.youtube.com/vi/%s/hqdefault.jpg"
    }
}
