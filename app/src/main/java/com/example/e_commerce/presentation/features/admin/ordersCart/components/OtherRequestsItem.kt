package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce.domain.models.RequestToAdmin

@Composable
fun OtherRequestsItem(
    modifier: Modifier = Modifier,
    requestToAdmin: RequestToAdmin,
    isExpanded: Boolean,
    onItemClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                onItemClick()
            },
        tonalElevation = 12.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier,
        ) {
            RequestItem(
                requestToAdmin = requestToAdmin,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isExpanded) {
                Spacer(modifier = Modifier.height(32.dp))
                requestToAdmin.orders.forEach { item ->
                    UserOrderItem(order = item)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
