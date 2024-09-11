package com.example.avito.data.repository

import com.example.avito.data.mapper.Mapper
import com.example.avito.data.network.retrofit.ApiFactory
import com.example.avito.data.network.retrofit.utils.getErrorResponse
import com.example.avito.domain.Product

class ProductsRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()


    fun getAllProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getAllProducts()
            if (response.isSuccessful) {
                Result.success(response.body()?.productList?.let { mapper.listProductDtoToEntity(it) }
                    ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.getErrorResponse()?.message ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(id: String): Product {
        return mapper.productDtoToEntity(apiService.getProduct(id).product)
    }
}