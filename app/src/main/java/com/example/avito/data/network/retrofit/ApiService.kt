package com.example.avito.data.network.retrofit

import com.example.avito.data.network.models.auth.LogInRequest
import com.example.avito.data.network.models.auth.SignInRequest
import com.example.avito.data.network.models.auth.SignInResponse
import com.example.avito.data.network.models.product.ProductListResponseDto
import com.example.avito.data.network.models.product.ProductResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("app/v1/users")
    suspend fun logInUser(@Body user: LogInRequest): Response<Unit>

    @POST("app/v1/users/auth/login")
    suspend fun signInUser(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @GET("users/auth/login")
    suspend fun getProfile(@Header("Authorization") token: String): Response<Unit>

    @GET("app/v1/products/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: String): ProductResponseDto

    @GET("app/v1/products")
    suspend fun getAllProducts(): Response<ProductListResponseDto>

    @GET("app/v1/products")
    suspend fun getProductsFilterByCategory(
        @Query("category") category: String
    ): Response<ProductListResponseDto>

    @GET("app/v1/products")
    suspend fun getProductsWithPriceSort(
        @Query("sort", encoded = true) sort: String
    ): Response<ProductListResponseDto>

    @GET("app/v1/products")
    suspend fun getProductsWithPriceSortAndFilterByCategory(
        @Query("sort", encoded = true) sort: String,
        @Query("category") category: String,
    ): Response<ProductListResponseDto>

}