package com.example.avito.data.network.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("__v")
    val __v: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("discounted_price")
    val discounted_price: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_rating")
    val product_rating: Double,
    @SerializedName("product_specifications")
    val product_specifications: List<ProductSpecification>
)