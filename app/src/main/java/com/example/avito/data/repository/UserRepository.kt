package com.example.avito.data.repository

import com.example.avito.data.network.models.auth.LogInRequest
import com.example.avito.data.network.models.auth.SignInRequest
import com.example.avito.data.network.retrofit.AuthApiFactory
import com.example.avito.data.network.retrofit.utils.getErrorResponse

class UserRepository {
    private val apiService = AuthApiFactory.apiService

    suspend fun logInUser(user: LogInRequest): Result<Unit> {
        return try {
            val response = apiService.logInUser(user)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed: ${response.getErrorResponse()?.message ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile(token: String): Result<Unit> {
        return try {
            val response = apiService.getProfile("Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("GetProfile Error: ${response.getErrorResponse()?.message ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            Result.failure(e)

        }
    }

    suspend fun signInUser(signInRequest: SignInRequest): Result<String> {
        return try {
            val response = apiService.signInUser(signInRequest)
            if (response.isSuccessful) {
                Result.success(response.body()?.token ?: "")
            } else {
                Result.failure(Exception("SignIn failed: ${response.getErrorResponse()?.message ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}