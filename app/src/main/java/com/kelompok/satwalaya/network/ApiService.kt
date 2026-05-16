package com.kelompok.satwalaya.network

import com.kelompok.satwalaya.model.LayananResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/posts.php")
    suspend fun getLayanan(): List<LayananResponse>
}