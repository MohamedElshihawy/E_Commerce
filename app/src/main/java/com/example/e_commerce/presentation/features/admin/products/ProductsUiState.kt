package com.example.e_commerce.presentation.features.admin.products

import com.example.e_commerce.domain.models.Product

data class ProductsUiState(
    val products: List<Product> = emptyList(),
)
