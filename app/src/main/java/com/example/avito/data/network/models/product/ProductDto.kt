package com.example.avito.data.network.models.product

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("discounted_price")
    val discountedPrice: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_rating")
    val productRating: Double,
    @SerializedName("product_specifications")
    val productSpecifications: List<ProductSpecificationDto>
)