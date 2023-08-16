package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.repository.CartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class AddOrderToCartUseCase(
    private val repository: CartOperationsRepository,
) {

    suspend operator fun invoke(userId: String, order: UserOrder): Flow<Resource<Boolean>> {
        return repository.addOrderToCart(
            userId = userId,
            order = order,
        )
    }
}
