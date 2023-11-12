package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.repository.UserCartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteAllOrdersFromCartUserCase(
    private val repository: UserCartOperationsRepository,
) {

    suspend operator fun invoke(userId: String): Flow<Resource<Boolean>> {
        return repository.deleteAllOrdersFromCart(userId = userId)
    }
}
