package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.e_commerce.ui.theme.gold

@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Int = 5,
    filledColor: Color = gold,
    unfilledColor: Color = Color.Gray,
    size: Int = 24,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
    ) {
        for (i in 1..maxRating) {
            val color = if (i <= rating) filledColor else unfilledColor
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .size(size.dp)
                    .clickable { onClick(i) },
            )
        }
    }
}
