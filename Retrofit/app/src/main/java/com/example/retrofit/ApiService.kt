package com.example.retrofit

import retrofit2.http.GET

interface ApiService {
    @GET("posts") // EndPoint del servicio web
    suspend fun getPosts(): List<Kardex>
}