package com.example.e_commerce.domain.useCases

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.repository.UserOperationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(
    private val repository: UserOperationRepository,
) {
    suspend operator fun invoke(userName: String): Flow<Resource<UserRegistrationEntity>> {
        return repository.getCurrentUser(userName = userName)
    }
}
