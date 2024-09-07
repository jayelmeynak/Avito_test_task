package com.example.avito.data.network.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)