package com.example.avito.data.auth

import com.example.avito.data.auth.models.LogInRequest
import com.example.avito.data.auth.models.LogInResponse
import com.example.avito.data.auth.models.SignInRequest
import com.example.avito.data.auth.models.SignInResponse
import com.example.avito.data.network.models.product.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("app/v1/users")
    suspend fun logInUser(@Body user: LogInRequest): Response<LogInResponse>

    @POST("auth/login")
    suspend fun signInUser(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @GET("app/v1/products/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: String): ProductResponse

    @GET("app/v1/products")
    fun getAllProducts(): List<ProductResponse>
}