package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.presentation.common.components.CustomButton

@Composable
fun AcceptedRequestItem(
    modifier: Modifier = Modifier,
    request: RequestToAdmin,
    isExpanded: Boolean = false,
    onItemClick: () -> Unit,
    onDeliveredButtonClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        OtherRequestsItem(
            requestToAdmin = request,
            isExpanded = isExpanded,
            onItemClick = { onItemClick() },
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Delivered",
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = White,
            ),
            onButtonClick = { onDeliveredButtonClick(request.date) },
        )
    }
}
