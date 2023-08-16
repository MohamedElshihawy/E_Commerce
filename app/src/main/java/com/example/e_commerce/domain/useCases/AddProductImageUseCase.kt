package com.example.e_commerce.domain.useCases

import android.net.Uri
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class AddProductImageUseCase(private val repository: ProductOperationsRepository) {

    operator fun invoke(uri: Uri): Flow<Resource<Uri?>> {
        return repository.uploadProductImage(uri = uri)
    }
}
