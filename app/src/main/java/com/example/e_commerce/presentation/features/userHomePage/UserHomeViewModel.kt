package com.example.e_commerce.presentation.features.userHomePage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserHomeViewModel(
    private val useCasesWrapper: UseCasesWrapper,
) : ViewModel() {

    private val _productsFullListState = mutableStateOf<List<Product>>(listOf())
    private val _productsFilteredListState = _productsFullListState
    val productsListState: State<List<Product>> = _productsFilteredListState

    private val _searchField = mutableStateOf(CustomTextFieldState())
    val searchField: State<CustomTextFieldState> = _searchField

    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    private val _lastScrollPosition = mutableStateOf(0)
    val lastScrollPosition: State<Int> = _lastScrollPosition

    private val _selectedCategory = mutableStateOf("")

    private var cashedProducts = listOf<Product>()

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    val networkRequestState: State<NetworkRequestState> = _networkRequestState

    init {
        getProducts()
    }

    fun onEvent(
        userHomeEvents: UserHomeEvents,
    ) {
        when (userHomeEvents) {
            is UserHomeEvents.ReloadAllProducts -> {
                getProducts()
            }

            is UserHomeEvents.FilterProductsByName -> {
                filterForProductsByName(userHomeEvents.name)
            }

            is UserHomeEvents.FilterProductsByCategory -> {
                _selectedCategory.value = userHomeEvents.category
                filterProductsByCategory(_selectedCategory.value)
            }

            is UserHomeEvents.AddProductToFavourite -> {
            }

            is UserHomeEvents.IsSearching -> {
                _isSearching.value = userHomeEvents.isSearching
            }

            is UserHomeEvents.EnteredSearchText -> {
                _searchField.value = _searchField.value.copy(
                    value = userHomeEvents.text,
                )
                filterForProductsByName(userHomeEvents.text)
            }

            is UserHomeEvents.UpdateCurrentScrollPosition -> {
                _lastScrollPosition.value = userHomeEvents.position
            }
        }
    }

    private fun filterProductsByCategory(category: String) {
        var filteredList: List<Product>
        val fullList = cashedProducts

        viewModelScope.launch(Dispatchers.Default) {
            filteredList = fullList.filter {
                it.category == category
            }
            withContext(Dispatchers.Main) {
                _productsFilteredListState.value = filteredList
            }
        }
    }

    private fun filterForProductsByName(name: String) {
        var filteredList: List<Product>
        val fullList = cashedProducts

        viewModelScope.launch(Dispatchers.Default) {
            filteredList = fullList.filter {
                it.category == _selectedCategory.value && it.name.contains(name)
            }
            withContext(Dispatchers.Main) {
                _productsFilteredListState.value = filteredList
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getAllProductsFromDB().invoke().collect { products ->
                when (products) {
                    is Resource.Loading -> {
                        _networkRequestState.value = _networkRequestState.value.copy(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _networkRequestState.value = _networkRequestState.value.copy(
                            isLoading = false,
                            isSuccess = "Downloaded Products Successfully",
                        )
                        withContext(Dispatchers.Main) {
                            _productsFullListState.value = products.data!!
                            cashedProducts = products.data
                        }
                    }

                    is Resource.Error -> {
                        _networkRequestState.value = _networkRequestState.value.copy(
                            isLoading = false,
                            isError = products.message!!,
                        )
                    }
                }
            }
        }
    }

    private suspend fun getAllProductsFromDB() =
        withContext(Dispatchers.IO) { useCasesWrapper.getAllProductsUseCase }
}
