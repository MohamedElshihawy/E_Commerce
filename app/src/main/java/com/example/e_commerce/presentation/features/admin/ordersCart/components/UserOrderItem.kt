package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.presentation.common.components.ImageFromLink

@Composable
fun UserOrderItem(
    modifier: Modifier = Modifier,
    order: UserOrder,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageFromLink(
                imageUrl = order.image,
                modifier = Modifier
                    .width(90.dp)
                    .height(110.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Fit,
            )

            Spacer(modifier = Modifier.width(16.dp))

            NameAndCategory(name = order.name, category = order.category)

            Spacer(modifier = Modifier.weight(1f))

            Column {
                StyledText(firstLabel = "Quantity: ", value = order.numOfRequestedItems.toString())
                Spacer(modifier = Modifier.weight(1f))
                StyledText(firstLabel = "Unit: $", value = order.unitPrice.toString())
                Spacer(modifier = Modifier.weight(1f))
                StyledText(firstLabel = "Total: $", value = order.numOfRequestedItems.toString())
            }
        }
    }
}

@Composable
fun NameAndCategory(
    modifier: Modifier = Modifier,
    name: String,
    category: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
            ),
        )
    }
}

@Composable
fun StyledText(
    modifier: Modifier = Modifier,
    firstLabel: String,
    value: String,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                ),
            ) {
                append(firstLabel)
            }
            withStyle(
                SpanStyle(
                    fontSize = 11.sp,
                ),
            ) {
                append(
                    value,
                )
            }
        },
        modifier = modifier,
    )
}

@Preview(
    showBackground = true,
)
@Composable
fun userOrder() {
    UserOrderItem(
        order = UserOrder(
            category = "cloths",
            productId = "",
            name = "Dress",
            numOfRequestedItems = 5,
            numOfAvailableItems = 0,
            image = "",
            unitPrice = 25.0f,
            totalPrice = 125.0f,
            date = "",
        ),
    )
}
