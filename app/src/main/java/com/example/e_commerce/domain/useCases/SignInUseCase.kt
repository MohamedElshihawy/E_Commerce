package com.example.e_commerce.domain.useCases

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val repository: AuthenticationRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<UserRegistrationEntity>> {
        return repository.login(email, password)
    }
}
