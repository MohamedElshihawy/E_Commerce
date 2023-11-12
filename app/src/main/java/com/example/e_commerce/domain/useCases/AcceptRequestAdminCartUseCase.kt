package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.repository.AdminCartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class AcceptRequestAdminCartUseCase(
    private val repository: AdminCartOperationsRepository,
) {
    suspend operator fun invoke(
        userId: String,
        requestToAdmin: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return repository.acceptRequest(userId = userId, request = requestToAdmin)
    }
}
