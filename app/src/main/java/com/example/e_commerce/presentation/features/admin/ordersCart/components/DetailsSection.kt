package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R

@Composable
fun DetailsSection(
    modifier: Modifier = Modifier,
    name: String,
    dateAndTime: String,
    address: String,
    city: String,
    quantity: String,
    totalPrice: String,
) {
    Column(modifier = modifier) {
        RowText(
            firstLabel = name,
            secondLabel = "Quantity: $quantity",
        )

        Spacer(modifier = Modifier.height(8.dp))

        RowText(
            firstLabel = "City: $city",
            secondLabel = "$$totalPrice",
        )

        Spacer(modifier = Modifier.height(8.dp))

        RowText(
            firstLabel = dateAndTime.split("\\")[0],
            secondLabel = dateAndTime.split("\\")[1],
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                ) {
                    append("${stringResource(id = R.string.address)}: ")
                }
                withStyle(
                    style = SpanStyle(),
                ) {
                    append(address)
                }
            },
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun RowText(
    modifier: Modifier = Modifier,
    firstLabel: String,
    secondLabel: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = firstLabel.take(32),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.weight(1f))

        Text(
            text = secondLabel,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
