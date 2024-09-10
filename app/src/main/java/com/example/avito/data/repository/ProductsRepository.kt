package com.example.avito.data.repository

import com.example.avito.data.mapper.Mapper
import com.example.avito.data.network.retrofit.ApiFactory
import com.example.avito.domain.Product

class ProductsRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()


    fun getAllProducts(): List<Product> {
        return mapper.listProductDtoToEntity(apiService.getAllProducts())
    }

    suspend fun getProduct(id: String): Product {
        return mapper.productDtoToEntity(apiService.getProduct(id).product)
    }
}