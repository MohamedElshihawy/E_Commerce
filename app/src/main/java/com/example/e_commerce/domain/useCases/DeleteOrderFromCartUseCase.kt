package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.repository.UserCartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteOrderFromCartUseCase(
    private val repository: UserCartOperationsRepository,
) {

    suspend operator fun invoke(
        userId: String,
        orderDate: String,
    ): Flow<Resource<Boolean>> {
        return repository.deleteOrderFromCart(
            userId = userId,
            orderDate = orderDate,
        )
    }
}
