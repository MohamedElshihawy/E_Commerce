package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce.domain.models.RequestToAdmin

@Composable
fun RequestItemWrapper(
    modifier: Modifier = Modifier,
    requestType: String,
    request: RequestToAdmin,
    isExpanded: Boolean = false,
    onItemClick: () -> Unit,
    onAcceptClick: (String) -> Unit = {},
    onRejectClick: (String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(4.dp),
    ) {
        when (requestType) {
            "Waiting" -> {
                NewRequestItem(
                    requestToAdmin = request,
                    isExpanded = isExpanded,
                    onItemClick = { onItemClick() },
                    onAcceptClick = { onAcceptClick(request.date) },
                    onRejectClick = { onRejectClick(request.date) },
                    modifier = Modifier,
                )
            }

            "Accepted" -> {
                AcceptedRequestItem(
                    request = request,
                    onItemClick = { onItemClick() },
                    onDeliveredButtonClick = {},
                )
            }

            else -> {
                OtherRequestsItem(
                    requestToAdmin = request,
                    isExpanded = isExpanded,
                    onItemClick = { onItemClick() },
                )
            }
        }
    }
}
