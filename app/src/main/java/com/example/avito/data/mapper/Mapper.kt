package com.example.avito.data.mapper

import com.example.avito.data.network.models.product.ProductDto
import com.example.avito.data.network.models.product.ProductSpecificationDto
import com.example.avito.domain.Product
import com.example.avito.domain.ProductSpecification

class Mapper {

    fun productDtoToEntity(product: ProductDto): Product {
        return Product(
            id = product.id,
            images = product.images,
            discountedPrice = product.discountedPrice,
            price = product.price,
            name = product.name,
            description = product.description,
            category = product.category,
            productSpecifications = listSpecificationMapper(product.productSpecifications)
        )
    }

    fun specificationDtoToEntity(specificationDto: ProductSpecificationDto): ProductSpecification {
        return ProductSpecification(
            key = specificationDto.key,
            value = specificationDto.value
        )
    }

    fun listSpecificationMapper(list: List<ProductSpecificationDto>): List<ProductSpecification> {
        return list.map {
            specificationDtoToEntity(it)
        }
    }

    fun listProductDtoToEntity(list: List<ProductDto>): List<Product> {
        return list.map {
            productDtoToEntity(it)
        }
    }
}