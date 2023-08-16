package com.example.e_commerce.presentation.features.addNewProduct

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.R
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.CustomTextField
import com.example.e_commerce.presentation.common.components.loadingAnimations.DialogBoxLoading
import com.example.e_commerce.presentation.features.addNewProduct.components.ImagePicker
import com.example.e_commerce.presentation.common.components.SizesSection
import org.koin.androidx.compose.get

@Composable
fun AddNewProductScreen(
    modifier: Modifier = Modifier,
    categoryName: String,
    viewModel: AddNewProductViewModel = get(),
) {
    val nameState by viewModel.pNameState
    val descriptionState by viewModel.pDescription
    val numOfItemsState by viewModel.pNumOfItems
    val priceState by viewModel.pPrice
    val availableSizesState = viewModel.availableSizes
    val pickedImageUri = viewModel.pImage

    val networkRequestState = remember {
        viewModel.networkRequestState
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.onEvent(
            AddNewProductEvents.StoreProductCategory(categoryName),
        )
    })

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImagePicker(
            onImagePicked = { imageUri ->
                viewModel.onEvent(
                    AddNewProductEvents
                        .PickProductImage(imageUri.toString()),
                )
            },
            pickedUri = pickedImageUri.value.toString(),
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (categoryName == "Cloths") {
            SizesSection(
                allSizes = viewModel.getClothsSizes(),
                productSelectedSizes = availableSizesState,
                onClick = { selectedSize ->
                    viewModel.onEvent(
                        AddNewProductEvents
                            .PickAvailableSizes(selectedSize),
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeHolder = "Product Name",
            value = nameState.value,
            onValueChange = {
                viewModel
                    .onEvent(
                        AddNewProductEvents.NameEntered(it),
                    )
            },
            onTrailingIconClick = {
                viewModel.onEvent(AddNewProductEvents.NameEntered(""))
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeHolder = stringResource(id = R.string.product_description),
            value = descriptionState.value,
            onValueChange = {
                viewModel
                    .onEvent(
                        AddNewProductEvents.DescriptionEntered(it),
                    )
            },
            isSingleLine = false,
            onTrailingIconClick = {
                viewModel.onEvent(AddNewProductEvents.DescriptionEntered(""))
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeHolder = stringResource(id = R.string.product_price),
            value = priceState.value,
            onValueChange = {
                viewModel
                    .onEvent(
                        AddNewProductEvents.PriceEntered(it),
                    )
            },
            keyboardOptions = KeyboardOptions.Default
                .copy(keyboardType = KeyboardType.Number),
            onTrailingIconClick = {
                viewModel.onEvent(AddNewProductEvents.PriceEntered(""))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeHolder = stringResource(id = R.string.num_of_items),
            value = numOfItemsState.value,
            onValueChange = {
                viewModel
                    .onEvent(
                        AddNewProductEvents.NumberOfItemsEntered(it),
                    )
            },
            keyboardOptions = KeyboardOptions.Default
                .copy(keyboardType = KeyboardType.Number),
            onTrailingIconClick = {
                viewModel.onEvent(AddNewProductEvents.NumberOfItemsEntered(""))
            },
        )

        Spacer(modifier = Modifier.height(36.dp))

        CustomButton(text = stringResource(id = R.string.add_product)) {
            viewModel.onEvent(AddNewProductEvents.AddNewProductToStore)
        }
    }

    val context = LocalContext.current

    if (networkRequestState.value.isLoading) {
        DialogBoxLoading()
    }

    LaunchedEffect(key1 = networkRequestState.value.isError) {
        if (networkRequestState.value.isError.isNotEmpty()) {
            Toast.makeText(context, networkRequestState.value.isError, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = networkRequestState.value.isSuccess) {
        if (networkRequestState.value.isSuccess.isNotEmpty()) {
            Toast.makeText(context, networkRequestState.value.isSuccess, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true, widthDp = 394)
@Composable
fun prev() {
    AddNewProductScreen(categoryName = "Product")
}
