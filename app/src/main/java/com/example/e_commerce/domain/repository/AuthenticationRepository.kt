package com.example.e_commerce.domain.repository

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun login(userName: String, password: String): Flow<Resource<UserRegistrationEntity>>

    suspend fun createAccount(userRegistration: UserRegistration): Flow<Resource<UserRegistration>>
}
