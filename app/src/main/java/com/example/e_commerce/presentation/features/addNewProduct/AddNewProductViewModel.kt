package com.example.e_commerce.presentation.features.addNewProduct

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.TimeDateFormatting
import kotlinx.coroutines.launch

class AddNewProductViewModel(
    private val useCasesWrapper: UseCasesWrapper,
) : ViewModel() {

    fun getClothsSizes() = listOf("S", "M", "L", "XL", "XXL")

    private val _pCategory = mutableStateOf("")

    private val _pNameState = mutableStateOf(CustomTextFieldState())
    val pNameState: State<CustomTextFieldState> = _pNameState

    private val _pDescription = mutableStateOf(CustomTextFieldState())
    val pDescription: State<CustomTextFieldState> = _pDescription

    private val _pNumOfItems = mutableStateOf(CustomTextFieldState())
    val pNumOfItems: State<CustomTextFieldState> = _pNumOfItems

    private val _pPrice = mutableStateOf(CustomTextFieldState())
    val pPrice: State<CustomTextFieldState> = _pPrice

    private val _pImage = mutableStateOf<Uri?>(null)
    val pImage: State<Uri?> = _pImage
    private val _availableSizes = mutableStateListOf<String>()
    val availableSizes = _availableSizes

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    val networkRequestState: State<NetworkRequestState> = _networkRequestState

    fun onEvent(addProductEvent: AddNewProductEvents) {
        when (addProductEvent) {
            is AddNewProductEvents.StoreProductCategory -> {
                _pCategory.value = addProductEvent.category
            }

            is AddNewProductEvents.NameEntered -> {
                _pNameState.value = _pNameState.value.copy(
                    value = addProductEvent.name,
                    showError = addProductEvent.name.isEmpty(),
                )
            }

            is AddNewProductEvents.DescriptionEntered -> {
                _pDescription.value = _pDescription.value.copy(
                    value = addProductEvent.description,
                )
            }

            is AddNewProductEvents.PriceEntered -> {
                _pPrice.value = _pPrice.value.copy(
                    value = addProductEvent.price,
                    showError = (addProductEvent.price.isEmpty() || !addProductEvent.price.isDigitsOnly()),
                )
            }

            is AddNewProductEvents.NumberOfItemsEntered -> {
                _pNumOfItems.value = _pNumOfItems.value.copy(
                    value = addProductEvent.number,
                    showError = (
                        addProductEvent.number.isEmpty() ||
                            !addProductEvent.number.isDigitsOnly()
                        ),
                )
            }

            is AddNewProductEvents.PickProductImage -> {
                _pImage.value = addProductEvent.uri.toUri()
            }

            is AddNewProductEvents.PickAvailableSizes -> {
                if (_availableSizes.contains(addProductEvent.sizes)) {
                    _availableSizes.remove(addProductEvent.sizes)
                } else {
                    _availableSizes.add(addProductEvent.sizes)
                }
            }

            is AddNewProductEvents.AddNewProductToStore -> {
                viewModelScope.launch {
                    useCasesWrapper.addProductImageUseCase(_pImage.value!!)
                        .collect { uploadResult ->
                            when (uploadResult) {
                                is Resource.Loading -> {
                                    _networkRequestState.value = _networkRequestState.value.copy(
                                        isLoading = true,
                                    )
                                }

                                is Resource.Success -> {
                                    val productImageUrl = uploadResult.data!!
                                    useCasesWrapper.addNewProductUseCase(
                                        Product(
                                            id = TimeDateFormatting.formattedTimeAndDate,
                                            imagesUrl = productImageUrl.toString(),
                                            name = _pNameState.value.value,
                                            description = _pDescription.value.value,
                                            price = _pPrice.value.value,
                                            availableSizes = _availableSizes.toList(),
                                            numberOfItems = _pNumOfItems.value.value.toInt(),
                                            category = _pCategory.value,
                                            uploadData = TimeDateFormatting.formattedTimeAndDate,
                                        ),
                                    ).collect { product ->
                                        when (product) {
                                            is Resource.Loading -> {
                                            }

                                            is Resource.Success -> {
                                                _networkRequestState.value =
                                                    _networkRequestState.value.copy(
                                                        isLoading = false,
                                                        isSuccess = "Uploaded product successfully",
                                                    )
                                                resetScreen()
                                            }

                                            is Resource.Error -> {
                                                _networkRequestState.value =
                                                    _networkRequestState.value.copy(
                                                        isLoading = false,
                                                        isError = product.message!!,
                                                    )
                                            }
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _networkRequestState.value = _networkRequestState.value.copy(
                                        isLoading = false,
                                        isError = uploadResult.message!!,
                                    )
                                }
                            }
                        }
                }
            }
        }
    }

    private fun resetScreen() {
        _availableSizes.clear()

        _pNameState.value = CustomTextFieldState(value = "")
        _pImage.value = null
        _pPrice.value = CustomTextFieldState(value = "")
        _pNumOfItems.value = CustomTextFieldState(value = "")
        _pDescription.value = CustomTextFieldState(value = "")
        _networkRequestState.value = NetworkRequestState()
    }
}
