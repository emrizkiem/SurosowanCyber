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
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjOWM2YjhmZTI0NDc0ODQzN2Q1M2M1MGU5NmE2NzhmMiIsIm5iZiI6MTcyODE4ODc4Ny4yMDI4ODUsInN1YiI6IjVkMTMxZTU5MGUwYTI2MzdjMmNkNmRiZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.V0XVCMdqQphp-I555nAUPoB5rZDG0S1yb7_ulry58OM")
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
    }
}