package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.presentation.common.components.ImageFromLink

@Composable
fun RequestItem(
    modifier: Modifier = Modifier,
    requestToAdmin: RequestToAdmin,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageFromLink(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                imageUrl = requestToAdmin.userImage,
            )

            Spacer(modifier = Modifier.width(16.dp))

            DetailsSection(
                name = requestToAdmin.userName,
                dateAndTime = requestToAdmin.date,
                address = requestToAdmin.address,
                city = requestToAdmin.city,
                quantity = requestToAdmin.orders.size.toString(),
                totalPrice = (requestToAdmin.orders.sumOf { it.totalPrice.toDouble() }).toString(),
            )
        }
    }
}

val req = RequestToAdmin(
    orders = listOf(),
    userName = "Mohamed Reda",
    city = "Dammietta",
    userImage = "",
    address = "dammietta\\sharabas",
    phoneNumber = "01126931750",
    date = "2023-8-21\\14:33",
    status = "not shipped",
)

@Preview(showBackground = true, widthDp = 400)
@Composable
fun previ() {
    RequestItem(
        requestToAdmin = req,
    )
}
