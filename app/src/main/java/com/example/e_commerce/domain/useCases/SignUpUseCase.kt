package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(
    private val repository: AuthenticationRepository,
) {

    suspend operator fun invoke(userRegistration: UserRegistration): Flow<Resource<UserRegistration>> {
        return repository.createAccount(userRegistration)
    }
}
