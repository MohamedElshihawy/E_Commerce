package com.example.e_commerce.presentation.features.productDetails

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_NAME_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.TimeDateFormatting.formatCurrentDateAndTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private var userName = ""

    private val _productState = mutableStateOf<Product?>(null)
    val productState: State<Product?> = _productState

    private val _collapseAvailableSizes = mutableStateOf(true)
    val collapseAvailableSizes: State<Boolean> = _collapseAvailableSizes

    private val _collapseProductDescription = mutableStateOf(true)
    val collapseProductDescription: State<Boolean> = _collapseProductDescription

    private val _collapseWriteReviewSection = mutableStateOf(true)
    val collapseWriteReviewSection: State<Boolean> = _collapseWriteReviewSection

    private val _reviewTextFieldState = mutableStateOf(CustomTextFieldState())
    val reviewTextFieldState: State<CustomTextFieldState> = _reviewTextFieldState

    private val _userRating = mutableStateOf(0)
    val userRating = _userRating

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    val networkRequestState: State<NetworkRequestState> = _networkRequestState

    init {
        viewModelScope.launch {
            PreferenceDataStoreHelper(context)
                .getPreference(USER_NAME_KEY, "")
                .collect {
                    userName = it
                }
        }
    }

    fun onEvent(productDetailsEvents: ProductDetailsEvents) {
        when (productDetailsEvents) {
            is ProductDetailsEvents.AddToFavourite -> {}
            is ProductDetailsEvents.CollapseProductDescription -> {
                _collapseProductDescription.value = productDetailsEvents.collapse
            }

            is ProductDetailsEvents.CollapseWriteReviewSection -> {
                _collapseWriteReviewSection.value = productDetailsEvents.collapse
            }

            is ProductDetailsEvents.CollapseProductAvailableSizes -> {
                _collapseAvailableSizes.value = productDetailsEvents.collapse
            }

            is ProductDetailsEvents.EnteredReviewText -> {
                _reviewTextFieldState.value = _reviewTextFieldState.value.copy(
                    value = productDetailsEvents.text,
                )
            }

            is ProductDetailsEvents.EnteredReviewStars -> {
                _userRating.value = productDetailsEvents.stars
            }

            is ProductDetailsEvents.SubmitReview -> {
                submitUserReview()
            }

            is ProductDetailsEvents.AddProductToCart -> {
                addOrderToCart()
            }
        }
    }

    fun getProduct(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.getSingleProductUseCase(productId)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Resource.Loading -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = true,
                                )
                            }

                            is Resource.Success -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isSuccess = "Loaded Product Data",
                                )
                                _productState.value = result.data
                            }

                            is Resource.Error -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isError = "couldn't loaded Product Data",
                                )
                            }
                        }
                    }
                }
        }
    }

    private suspend fun uploadUserReview() {
        val userReview = collectUserReview(userName)

        val newReviewsList = mutableListOf<ProductReviews>().apply {
            addAll(_productState.value!!.reviews.filterNotNull())
            add(userReview)
        }

        useCasesWrapper.uploadUserReview(
            averageRating = _productState.value!!.rating.toFloat(),
            productId = _productState.value!!.id,
            userRating = _userRating.value.toFloat(),
            reviews = newReviewsList,
        )
            .collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Loading -> {
                            _networkRequestState.value = _networkRequestState.value.copy(
                                isLoading = true,
                            )
                        }

                        is Resource.Success -> {
                            _networkRequestState.value = _networkRequestState.value.copy(
                                isLoading = false,
                                isSuccess = "Posted your review successfully",
                            )
                            _productState.value = _productState.value!!.copy(
                                reviews = result.data!!,
                            )
                        }

                        is Resource.Error -> {
                            _networkRequestState.value = _networkRequestState.value.copy(
                                isLoading = false,
                                isError = "Couldn't post our review successfully",
                            )
                        }
                    }
                }
            }
    }

    private fun collectUserReview(userName: String): ProductReviews {
        return ProductReviews(
            comment = _reviewTextFieldState.value.value,
            date = formatCurrentDateAndTime(System.currentTimeMillis()),
            userName = userName,
            rating = _userRating.value,
        )
    }

    private fun submitUserReview() {
        viewModelScope.launch(Dispatchers.Default) {
            val userFirstReview = _productState.value!!.reviews.none { it!!.userName == userName }

            if (userFirstReview) {
                if (_userRating.value > 0) {
                    withContext(Dispatchers.IO) {
                        uploadUserReview()
                    }
                    withContext(Dispatchers.Main) {
                        _reviewTextFieldState.value = _reviewTextFieldState.value.copy(value = "")
                        _collapseWriteReviewSection.value = !_collapseWriteReviewSection.value
                    }
                }
            }
        }
    }

    private fun addOrderToCart() {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.addOrderToCart(
                userId = userName,
                order = collectUserOrderData(),
            )
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Resource.Loading -> {
                                _networkRequestState.value =
                                    _networkRequestState.value.copy(
                                        isLoading = true,
                                    )
                            }

                            is Resource.Success -> {
                                _networkRequestState.value =
                                    _networkRequestState.value.copy(
                                        isLoading = false,
                                        isSuccess = "Added order to cart Successfully",
                                    )
                            }

                            is Resource.Error -> {
                                _networkRequestState.value =
                                    _networkRequestState.value.copy(
                                        isLoading = false,
                                        isError = "couldn't Add order to cart Successfully",
                                    )
                            }
                        }
                    }
                }
        }
    }

    private fun collectUserOrderData(): UserOrder {
        return UserOrder(
            productId = _productState.value!!.id,
            name = _productState.value!!.name,
            numOfAvailableItems = _productState.value!!.numberOfItems,
            image = _productState.value!!.imagesUrl,
            unitPrice = _productState.value!!.price.toFloat(),
            date = formatCurrentDateAndTime(System.currentTimeMillis()),
        )
    }
}
