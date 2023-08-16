package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.repository.CartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllOrdersInCartUseCase(
    private val repository: CartOperationsRepository,
) {

    suspend operator fun invoke(userId: String): Flow<Resource<List<UserOrder>>> {
        return repository.getAllOrdersInCart(userId)
    }
}
