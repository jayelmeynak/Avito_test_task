package com.example.avito.data.network.models.product

import com.google.gson.annotations.SerializedName

data class ProductListResponseDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("Data")
    val productList: List<ProductDto>
)
