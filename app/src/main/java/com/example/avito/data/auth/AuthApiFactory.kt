package com.example.avito.data.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiFactory {

    private const val BASE_URL = "https://fakeshopapi-l2ng.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)

}