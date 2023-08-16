package com.example.e_commerce.domain.useCases

import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.flow.Flow

class UploadUserReviewUseCase(private val repository: ProductOperationsRepository) {

    suspend operator fun invoke(
        productId: String,
        reviews: List<ProductReviews>,
        averageRating: Float,
        userRating: Float,
    ): Flow<Resource<List<ProductReviews>>> {
        return repository.uploadUserReviewOnProduct(
            productId = productId,
            reviews = reviews,
            averageRating = averageRating,
            userRating = userRating,
        )
    }
}
