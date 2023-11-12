package com.example.e_commerce.domain.repository

import android.net.Uri
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductOperationsRepository {

    fun addNewProduct(product: Product): Flow<Resource<Boolean>>

    fun uploadProductImage(uri: Uri): Flow<Resource<Uri?>>

    suspend fun getAllProducts(): Flow<Resource<List<Product>>>
    suspend fun getSingleProduct(id: String): Flow<Resource<Product>>

    suspend fun uploadUserReviewOnProduct(
        productId: String,
        reviews: List<ProductReviews>,
        averageRating: Float,
        userRating: Float,
    ): Flow<Resource<List<ProductReviews>>>

    suspend fun editProductDetails(
        productId: String,
        product: Product,
    ): Flow<Resource<Boolean>>

    suspend fun deleteProduct(
        productId: String,
        imageUrl: String,
    ): Flow<Resource<Boolean>>
}
