package com.example.avito.data.repository

import com.example.avito.data.auth.AuthApiFactory
import com.example.avito.data.mapper.Mapper
import com.example.avito.domain.Product

class ProductsRepository {

    private val apiService = AuthApiFactory.apiService
    private val mapper = Mapper()


    fun getAllProducts(): List<Product> {
        return mapper.listProductDtoToEntity(apiService.getAllProducts())
    }

    suspend fun getProduct(id: String): Product {
        return mapper.productDtoToEntity(apiService.getProduct(id).product)
    }
}