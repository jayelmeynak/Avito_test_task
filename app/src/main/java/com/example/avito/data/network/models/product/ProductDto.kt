package com.example.avito.data.network.models.product

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("description")
    val description: String?,
    @SerializedName("price")
    val price: Int,
    @SerializedName("discounted_price")
    val discountedPrice: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("product_specifications")
    val productSpecifications: List<ProductSpecificationDto>
)