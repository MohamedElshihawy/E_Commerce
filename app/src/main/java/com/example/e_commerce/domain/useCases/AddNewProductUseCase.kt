package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class AddNewProductUseCase(private val repository: ProductOperationsRepository) {

    operator fun invoke(product: Product): Flow<Resource<Boolean>> {
        return repository.addNewProduct(product)
    }
}
