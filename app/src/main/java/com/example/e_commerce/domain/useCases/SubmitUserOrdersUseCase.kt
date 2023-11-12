package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.repository.UserCartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class SubmitUserOrdersUseCase(
    private val repository: UserCartOperationsRepository,
) {

    suspend operator fun invoke(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return repository.submitUserOrderToAdmin(
            userId = userId,
            request = request,
        )
    }
}
