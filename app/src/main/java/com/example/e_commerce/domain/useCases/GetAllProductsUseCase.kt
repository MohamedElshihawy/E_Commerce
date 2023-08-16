package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllProductsUseCase(
    private val repository: ProductOperationsRepository,
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return repository.getAllProducts()
    }
}
