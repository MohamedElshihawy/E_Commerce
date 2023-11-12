package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.repository.AdminCartOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllRequestAdminCartUseCase(
    private val repository: AdminCartOperationsRepository,
) {

    suspend operator fun invoke(userId: String): Flow<Resource<Any>> {
        return repository.getAllRequests(userId)
    }
}
