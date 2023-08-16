package com.example.e_commerce.presentation.features.productDetails

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.ImageFromLink
import com.example.e_commerce.presentation.common.components.SizesSection
import com.example.e_commerce.presentation.features.productDetails.components.CollapsableItem
import com.example.e_commerce.presentation.features.productDetails.components.ProductMiniDescription
import com.example.e_commerce.presentation.features.productDetails.components.RatingSection
import com.example.e_commerce.presentation.features.productDetails.components.ReviewItem
import com.example.e_commerce.presentation.features.productDetails.components.TopBar
import com.example.e_commerce.presentation.features.productDetails.components.WriteReviewSection
import com.example.e_commerce.util.Constants.LAST_SCROLL_POSITION
import org.koin.androidx.compose.get

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productId: String,
    itemPosition: Int,
    viewModel: ProductDetailsViewModel = get(),
    navController: NavController,
) {
    val productState = viewModel.productState
    val isProductDescriptionCollapsed = viewModel.collapseProductDescription
    val isAvailableSizesCollapsed = viewModel.collapseAvailableSizes
    val reviewTextFieldState = viewModel.reviewTextFieldState
    val userRating = viewModel.userRating
    val collapseWriteReviewSection = viewModel.collapseWriteReviewSection
    val networkRequestState = viewModel.networkRequestState

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getProduct(productId)
    }

    if (networkRequestState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(12.dp),
                strokeWidth = 3.dp,
            )
        }
    } else if (networkRequestState.value.isSuccess.isNotEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier
                    .padding(bottom = 70.dp)
                    .verticalScroll(
                        rememberScrollState(),
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    ImageFromLink(
                        imageUrl = productState.value!!.imagesUrl,
                        modifier = Modifier.fillMaxSize().aspectRatio(.7f),
                    )

                    TopBar {
                        navController.navigate(
                            Screen.UserHomeScreen.route + "?$LAST_SCROLL_POSITION=$itemPosition",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(24.dp),
                ) {
                    ProductMiniDescription(
                        category = productState.value!!.category,
                        name = productState.value!!.name,
                        numInStock = productState.value!!.numberOfItems.toString(),
                        price = productState.value!!.price,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CollapsableItem(
                        text = "Product Details",
                        onClick = {
                            viewModel.onEvent(
                                ProductDetailsEvents
                                    .CollapseProductDescription(!isProductDescriptionCollapsed.value),
                            )
                        },
                        isCollapsed = isProductDescriptionCollapsed.value,
                    ) {
                        Text(text = productState.value!!.description)
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    if (productState.value!!.category == "Cloths") {
                        CollapsableItem(
                            text = "Available Sizes",
                            onClick = {
                                viewModel.onEvent(
                                    ProductDetailsEvents
                                        .CollapseProductAvailableSizes(!isAvailableSizesCollapsed.value),
                                )
                            },
                            isCollapsed = isAvailableSizesCollapsed.value,
                        ) {
                            SizesSection(
                                productSelectedSizes = productState.value!!.availableSizes,
                                onClick = {},
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    RatingSection(
                        averageRating = productState.value!!.rating.toFloat(),
                        numOfVotes = productState.value!!.reviews.size,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(productState.value?.reviews ?: emptyList()) {
                            if (it != null) {
                                ReviewItem(
                                    review = it,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedVisibility(visible = collapseWriteReviewSection.value) {
                        WriteReviewSection(
                            reviewText = reviewTextFieldState.value.value,
                            onReviewTextChange = {
                                viewModel.onEvent(ProductDetailsEvents.EnteredReviewText(it))
                            },
                            onTrailingIconClick = {
                                viewModel.onEvent(ProductDetailsEvents.EnteredReviewText(""))
                            },
                            onRatingChanged = {
                                viewModel.onEvent(ProductDetailsEvents.EnteredReviewStars(it))
                            },
                            rating = userRating.value.toFloat(),
                            onReviewSubmitted = {
                                viewModel.onEvent(ProductDetailsEvents.SubmitReview)
                            },
                            onButtonClick = {
                                viewModel
                                    .onEvent(
                                        ProductDetailsEvents
                                            .CollapseWriteReviewSection(!collapseWriteReviewSection.value),
                                    )
                            },
                            isCollapsed = collapseWriteReviewSection.value,
                        )
                    }
                }
            }

            CustomButton(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                onButtonClick = {
                    viewModel.onEvent(ProductDetailsEvents.AddProductToCart)
                },
                text = stringResource(id = R.string.add_to_cart),
            )
        }
    }

    LaunchedEffect(key1 = networkRequestState.value.isSuccess) {
        if (networkRequestState.value.isSuccess == "Added order to cart Successfully") {
            Toast.makeText(context, networkRequestState.value.isSuccess, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.UserHomeScreen.route + "?$LAST_SCROLL_POSITION=$itemPosition")
        }
    }
}
