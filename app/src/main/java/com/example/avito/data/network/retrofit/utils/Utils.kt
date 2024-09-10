package com.example.avito.data.network.retrofit.utils

import com.example.avito.data.network.models.auth.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.getErrorResponse(): ErrorResponse? {
    return try {
        this.errorBody()?.string()?.let {
            Gson().fromJson(it, ErrorResponse::class.java)
        }
    } catch (e: Exception) {
        null
    }
}