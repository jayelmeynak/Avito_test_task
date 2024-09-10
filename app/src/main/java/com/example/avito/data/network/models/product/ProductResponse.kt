package com.example.avito.data.network.models.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("data")
    val product: ProductDto,
    @SerializedName("status")
    val status: String
)