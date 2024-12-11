package com.example.retrovit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    // Obtener todos los datos desde la API
    @GET("posts")
    suspend fun getPostList(): Response<List<Post>>


}
