package com.kelompok.satwalaya.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2/PemrogramanMobile-Tugas7-241111022-AlexanderSaputraNadeak/"

    val instance: PostApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }
}