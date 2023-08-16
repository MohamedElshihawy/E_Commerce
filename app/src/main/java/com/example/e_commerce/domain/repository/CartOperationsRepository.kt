package com.example.e_commerce.domain.repository

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartOperationsRepository {

    suspend fun addOrderToCart(userId: String, order: UserOrder): Flow<Resource<Boolean>>
    suspend fun deleteOrderFromCart(userId: String, orderDate: String): Flow<Resource<Boolean>>
    suspend fun deleteAllOrdersFromCart(userId: String): Flow<Resource<Boolean>>
    suspend fun submitUserOrderToAdmin(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>>

    suspend fun getAllOrdersInCart(userId: String): Flow<Resource<List<UserOrder>>>
}
