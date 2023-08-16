package com.example.e_commerce.presentation.features.productDetails

sealed class ProductDetailsEvents {

    class AddToFavourite(val isFavourite: Boolean) : ProductDetailsEvents()
    class EnteredReviewText(val text: String) : ProductDetailsEvents()
    class EnteredReviewStars(val stars: Int) : ProductDetailsEvents()

    class CollapseProductDescription(val collapse: Boolean) : ProductDetailsEvents()
    class CollapseWriteReviewSection(val collapse: Boolean) : ProductDetailsEvents()
    class CollapseProductAvailableSizes(val collapse: Boolean) : ProductDetailsEvents()
    object AddProductToCart : ProductDetailsEvents()

    object SubmitReview : ProductDetailsEvents()
}
