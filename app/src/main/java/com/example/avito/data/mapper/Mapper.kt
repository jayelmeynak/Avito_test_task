package com.example.avito.data.mapper

import com.example.avito.data.network.models.product.ProductDto
import com.example.avito.domain.Product

class Mapper {

    fun productDtoToEntity(product: ProductDto): Product {
        return Product(
            id = product.id,
            images = product.images,
            discountedPrice = product.discountedPrice,
            price = product.price,
            name = product.name,
            description = product.description,
            category = product.category
        )
    }


    fun listProductDtoToEntity(list: List<ProductDto>): List<Product> {
        return list.map {
            productDtoToEntity(it)
        }
    }
}