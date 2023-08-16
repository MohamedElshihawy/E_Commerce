package com.example.e_commerce.presentation.features.productDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductMiniDescription(
    modifier: Modifier = Modifier,
    category: String,
    name: String,
    numInStock: String,
    price: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        NameAndPrice(
            name = name,
            price = price,
        )

        Spacer(modifier = Modifier.height(8.dp))

        MoreInfo(
            numInStock = numInStock,
        )
    }
}

@Composable
private fun NameAndPrice(
    name: String,
    price: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth(.5f),
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "\$$price",
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MoreInfo(
    modifier: Modifier = Modifier,
    numInStock: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconWithText(
            icon = Icons.Default.Store,
            contentDescription = "In Stock",
            text = "$numInStock In Stock",
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconWithText(
            icon = Icons.Default.Restore,
            contentDescription = "Return Product in 30 days",
            text = "30 days returns",
        )
    }
}

@Composable
private fun IconWithText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PDes() {
    ProductMiniDescription(
        category = "Cloths",
        name = "Dress is Good ",
        price = "$1560",
        numInStock = "4",
    )
}
