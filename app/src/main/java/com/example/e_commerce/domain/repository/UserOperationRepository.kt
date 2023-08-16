package com.example.e_commerce.domain.repository

import android.net.Uri
import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserOperationRepository {

    suspend fun getCurrentUser(userName: String): Flow<Resource<UserRegistrationEntity>>

    suspend fun updateUserData(userRegistration: UserRegistrationEntity): Flow<Resource<UserRegistrationEntity>>

    suspend fun uploadUserProfileImage(uri: Uri): Flow<Resource<Uri?>>
}
