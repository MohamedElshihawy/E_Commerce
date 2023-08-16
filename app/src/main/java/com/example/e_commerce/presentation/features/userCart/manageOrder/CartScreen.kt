package com.example.e_commerce.presentation.features.userCart.manageOrder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.loadingAnimations.DialogBoxLoading
import com.example.e_commerce.presentation.features.userCart.manageOrder.components.CartTopBar
import com.example.e_commerce.presentation.features.userCart.manageOrder.components.OrderItem
import com.example.e_commerce.presentation.features.userCart.manageOrder.components.SelectAllOrdersSection
import org.koin.androidx.compose.get

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = get(),
    navController: NavController,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserOrders()
    }

    val totalProductsPrice = viewModel.totalPriceState
    val orders = viewModel.userOrdersState
    val counters = viewModel.itemsCounters
    val numOfSelectedItems = viewModel.numOfSelectedItems
    val networkState = viewModel.networkRequestState

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        ) {
            CartTopBar(
                onBackClick = {
                    navController.navigate(Screen.UserHomeScreen.route)
                },
            )
            Column(
                Modifier.fillMaxSize()
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.cart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(start = 12.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                SelectAllOrdersSection(
                    numOfItems = numOfSelectedItems.value,
                    onCheckChange = {},
                    onDeleteSelectedItemsClick = {},
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(orders.value) { order ->
                        viewModel.onEvent(CartEvents.CreateNewCounter(order.date))
                        val associatedCounter = counters.find { it.id == order.date }

                        if (associatedCounter != null) {
                            OrderItem(
                                isSelected = associatedCounter.isSelected,
                                userOrder = order,
                                counterValue = associatedCounter.count,
                                onDeleteClick = { id ->
                                    viewModel.onEvent(
                                        CartEvents.DeleteSingleItem(id),
                                    )
                                },
                                onPlusClick = { counterId ->
                                    viewModel.onEvent(CartEvents.IncreaseCounter(counterId = counterId))
                                },
                                onMinusClick = { counterId ->
                                    viewModel.onEvent(CartEvents.DecreaseCounter(counterId = counterId))
                                },
                                onItemSelectedListener = {
                                    viewModel.onEvent(CartEvents.SelectSingleItem(itemId = order.productId))
                                },
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total Price $${totalProductsPrice.value}",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomButton(
                text = "Next",
                onButtonClick = {
                    navController.navigate(Screen.ConfirmOrderCartScreen.route)
                },
            )
        }

        if (networkState.value.isLoading) {
            DialogBoxLoading()
        }
    }
}
