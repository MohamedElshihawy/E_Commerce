package com.example.e_commerce.presentation.features.userHomePage

sealed class UserHomeEvents {

    object ReloadAllProducts : UserHomeEvents()
    class IsSearching(val isSearching: Boolean) : UserHomeEvents()
    class EnteredSearchText(val text: String) : UserHomeEvents()
    class FilterProductsByName(val name: String) : UserHomeEvents()
    class FilterProductsByCategory(val category: String) : UserHomeEvents()
    class AddProductToFavourite(val add: Boolean) : UserHomeEvents()

    class UpdateCurrentScrollPosition(val position: Int) : UserHomeEvents()
}
