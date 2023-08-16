package com.example.e_commerce.presentation.features.userHomePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.AppBar
import com.example.e_commerce.presentation.features.userHomePage.components.CategoriesList
import com.example.e_commerce.presentation.features.userHomePage.components.NetworkError
import com.example.e_commerce.presentation.features.userHomePage.components.ProductItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(FlowPreview::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    startScrollPosition: Int = 0,
    viewModel: UserHomeViewModel = get(),
    drawerState: DrawerState,
    navController: NavController,
) {
    val products = viewModel.productsListState
    val searchFieldState = viewModel.searchField
    val isSearching = viewModel.isSearching
    val lastScrollPosition = viewModel.lastScrollPosition
    val networkRequestState = viewModel.networkRequestState

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = startScrollPosition,
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        AppBar(
            isSearching = isSearching.value,
            searchText = searchFieldState.value.value,
            onSearchValueChange = {
                viewModel.onEvent(UserHomeEvents.EnteredSearchText(it))
            },
            onSearchSubmit = {
                viewModel.onEvent(UserHomeEvents.FilterProductsByName(it))
            },
            onSearchIconClick = {
                viewModel.onEvent(UserHomeEvents.IsSearching(!isSearching.value))
            },
            onNavIconClick = {
                scope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            },
        )

        Column(
            Modifier
                .padding(8.dp),
        ) {
            StyledHeaderText()

            Spacer(modifier = Modifier.height(8.dp))

            CategoriesList { selectedCategory ->
                viewModel.onEvent(UserHomeEvents.FilterProductsByCategory(selectedCategory))
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (networkRequestState.value.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(80.dp),
                    )
                }
            } else {
                if (networkRequestState.value.isError.isNotEmpty()) {
                    NetworkError(
                        onClick = {
                            viewModel.onEvent(
                                UserHomeEvents.ReloadAllProducts,
                            )
                        },
                    )
                } else if (networkRequestState.value.isSuccess.isNotEmpty()) {
                    LaunchedEffect(gridState) {
                        snapshotFlow { gridState.firstVisibleItemIndex }
                            .distinctUntilChanged()
                            .debounce(500L)
                            .collectLatest { visibleItemIndex ->
                                viewModel.onEvent(
                                    UserHomeEvents.UpdateCurrentScrollPosition(
                                        visibleItemIndex,
                                    ),
                                )
                            }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        state = gridState,
                    ) {
                        items(products.value) { item ->
                            ProductItem(
                                productId = item.id,
                                productPrice = item.price.toInt(),
                                productName = item.name,
                                productImage = item.imagesUrl,
                                onItemClick = {
                                    navController
                                        .navigate(
                                            Screen.ProductDetails.addArgs(
                                                it,
                                                lastScrollPosition.value.toString(),
                                            ),
                                        )
                                },
                                onIconClick = {},
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StyledHeaderText(
    modifier: Modifier = Modifier,
) {
    Text(

        text = buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(
                    lineHeight = 8.sp,
                ),
            ) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 32.sp,
                    ),
                ) {
                    append("I am \n")
                }
            }

            withStyle(
                style = SpanStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            ) {
                append("Looking For...")
            }
        },
        modifier = modifier
            .fillMaxHeight(.15f),
    )
}
