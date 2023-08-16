package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val repository: AuthenticationRepository,
) {

    suspend operator fun invoke(
        dbParentNode: String,
        email: String,
        password: String,
    ): Flow<Resource<UserRegistration>> {
        return repository.login(dbParentNode, email, password)
    }
}
