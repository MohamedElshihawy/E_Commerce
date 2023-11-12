package com.example.e_commerce.presentation.features.admin.ordersCart

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.presentation.common.components.loadingAnimations.DialogBoxLoading
import com.example.e_commerce.presentation.features.admin.ordersCart.components.HeaderText
import com.example.e_commerce.presentation.features.admin.ordersCart.components.RequestItemWrapper
import com.example.e_commerce.presentation.features.admin.ordersCart.components.RequestsTypesSection
import com.example.e_commerce.presentation.features.user.productDetails.components.TopBar
import org.koin.androidx.compose.get

@Composable
fun AdminOrdersCartScreen(
    modifier: Modifier = Modifier,
    viewModel: AdminCartViewModel = get(),
    navController: NavController,
) {
    val requestsList = viewModel.userRequestsState
    val networkRequestState = viewModel.networkRequestState
    val expandedItemsState = viewModel.expandedItems
    val requestFilterVisible = viewModel.requestsSectionVisible
    val currentRequestsType = viewModel.selectedRequestsType

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllRequests()
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize(),
        ) {
            TopBar {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.height(16.dp))

            HeaderText(
                numOfItems = requestsList.value.size,
                requestsType = currentRequestsType.value,
                filterListVisible = requestFilterVisible.value,
                onFilterIconClick = {
                    viewModel.onEvent(AdminCartEvents.ToggleFilterList)
                },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            AnimatedVisibility(visible = requestFilterVisible.value) {
                RequestsTypesSection(
                    selectedButton = currentRequestsType.value,
                    onButtonSelected = {
                        viewModel.onEvent(
                            AdminCartEvents.FilterRequests(it),
                        )
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(requestsList.value) { request ->
                    viewModel.onEvent(AdminCartEvents.CreateOrderState(id = request.date))
                    val associatedState = expandedItemsState.first { it.id == request.date }
                    RequestItemWrapper(
                        requestType = currentRequestsType.value,
                        request = request,
                        isExpanded = associatedState.isExpanded,
                        onAcceptClick = {
                            viewModel.onEvent(AdminCartEvents.AcceptOrder(request.date))
                        },
                        onRejectClick = {
                            viewModel.onEvent(AdminCartEvents.RejectOrder(request.date))
                        },
                        onItemClick = {
                            viewModel.onEvent(AdminCartEvents.ExpandRequestContent(request.date))
                        },
                    )
                }
            }
        }

        AnimatedVisibility(visible = networkRequestState.value.isLoading) {
            DialogBoxLoading()
        }

        val context = LocalContext.current

        LaunchedEffect(key1 = networkRequestState.value.isSuccess) {
            if (networkRequestState.value.isSuccess.isNotEmpty()) {
                Toast.makeText(context, networkRequestState.value.isSuccess, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        LaunchedEffect(key1 = networkRequestState.value.isError) {
            if (networkRequestState.value.isError.isNotEmpty()) {
                Toast.makeText(context, networkRequestState.value.isError, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
