package com.example.e_commerce.presentation.features.userHomePage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.presentation.common.components.ImageFromLink

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    productId: String,
    productPrice: Int,
    productName: String,
    productImage: String,
    onIconClick: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .clickable {
                onItemClick(productId)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProductTopSectionImage(
            productImage = productImage,
            productPrice = productPrice,
            onClick = onIconClick,
        )

        Text(
            text = productName,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
fun ProductTopSectionImage(
    modifier: Modifier = Modifier,
    productImage: String,
    productPrice: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(280.dp),
    ) {
        ImageFromLink(modifier = Modifier.fillMaxSize(), imageUrl = productImage)

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ClickableIcon(
                modifier = Modifier,
                onClick = onClick,
            )

            Spacer(modifier = Modifier.weight(1f))

            PriceTag(
                productPrice = productPrice,
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    isFavourite: Boolean = false,
    onClick: () -> Unit,
) {
    Icon(
        imageVector = if (!isFavourite) {
            Icons.Default.FavoriteBorder
        } else {
            Icons.Default.Favorite
        },
        contentDescription = "is Favourite icon",
        modifier = modifier
            .clickable {
                onClick()
            },
    )
}

@Composable
fun PriceTag(
    modifier: Modifier = Modifier,
    productPrice: Int,
) {
    Text(
        text = "  $$productPrice  ",
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        ),
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 12.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                ),
            )
            .background(Color.Gray),
    )
}
