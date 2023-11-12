package com.example.e_commerce.presentation.features.admin.addNewProduct

sealed class AddNewProductEvents {

    class StoreProductCategory(val category: String) : AddNewProductEvents()
    class NameEntered(val name: String) : AddNewProductEvents()
    class DescriptionEntered(val description: String) : AddNewProductEvents()
    class PriceEntered(val price: String) : AddNewProductEvents()
    class NumberOfItemsEntered(val number: String) : AddNewProductEvents()
    class PickProductImage(val uri: String) : AddNewProductEvents()
    class PickAvailableSizes(val sizes: String) : AddNewProductEvents()
    object AddNewProductToStore : AddNewProductEvents()
}
