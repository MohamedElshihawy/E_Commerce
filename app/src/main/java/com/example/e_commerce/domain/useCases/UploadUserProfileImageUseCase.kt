package com.example.e_commerce.domain.useCases

import android.net.Uri
import com.example.e_commerce.domain.repository.UserOperationRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class UploadUserProfileImageUseCase(
    private val repository: UserOperationRepository,
) {

    suspend operator fun invoke(uri: Uri): Flow<Resource<Uri?>> {
        return repository.uploadUserProfileImage(uri)
    }
}
