package com.example.avito.data.repository

import com.example.avito.data.auth.AuthApiFactory
import com.example.avito.data.auth.models.LogInRequest
import com.example.avito.data.auth.models.LogInResponse
import com.example.avito.data.auth.models.SignInRequest
import com.example.avito.data.auth.models.SignInResponse

class UserRepository {
    private val apiService = AuthApiFactory.apiService

    suspend fun logInUser(user: LogInRequest): Result<LogInResponse> {
        return try {
            val response = apiService.logInUser(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInUser(signInRequest: SignInRequest): Result<SignInResponse> {
        return try {
            val response = apiService.signInUser(signInRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}