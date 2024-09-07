package com.example.avito.data.auth

import com.example.avito.data.auth.models.LogInRequest
import com.example.avito.data.auth.models.LogInResponse
import com.example.avito.data.auth.models.SignInRequest
import com.example.avito.data.auth.models.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("app/v1/users")
    suspend fun logInUser(@Body user: LogInRequest): Response<LogInResponse>

    @POST("auth/login")
    suspend fun signInUser(@Body signInRequest: SignInRequest): Response<SignInResponse>

}