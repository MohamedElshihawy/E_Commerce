package com.example.e_commerce.domain.useCases

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.repository.UserOperationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserDataUseCase(
    private val repository: UserOperationRepository,
) {
    suspend operator fun invoke(userRegistrationEntity: UserRegistrationEntity): Flow<Resource<UserRegistrationEntity>> {
        return repository.updateUserData(userRegistrationEntity)
    }
}
