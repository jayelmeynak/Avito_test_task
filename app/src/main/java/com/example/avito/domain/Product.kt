package com.example.avito.domain

data class Product(
    val id: String,
    val images: List<String>,
    val discountedPrice: Int,
    val price: Int,
    val name: String,
    val description: String,
    val brand: String,
    val category: List<String>,
    val productRating: Double,
    val productSpecifications: List<ProductSpecification>,
    val v: Int
)
