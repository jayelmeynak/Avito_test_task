package com.example.avito.data.network.models.product

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    @SerializedName("data")
    val product: ProductDto,
    @SerializedName("status")
    val status: String
)