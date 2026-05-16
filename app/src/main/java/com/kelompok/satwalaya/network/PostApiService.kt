package com.kelompok.satwalaya.network

import com.kelompok.satwalaya.model.PostResponse
import retrofit2.http.GET

interface PostApiService {
    @GET("api/posts.php")
    suspend fun getPosts(): List<PostResponse>
}