package com.example.e_commerce.domain.repository

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

interface AdminCartOperationsRepository {

    suspend fun getAllRequests(userId: String): Flow<Resource<Any>>
    suspend fun acceptRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>>

    suspend fun rejectRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>>

    suspend fun deliveredRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>>
}
