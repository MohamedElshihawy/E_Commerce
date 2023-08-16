package com.example.e_commerce.presentation.features.userCart.manageOrder.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    count: Int = 1,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    Row(modifier = modifier) {
        Button(symbol = "-", onClick = { onMinusClick() })

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = count.toString(), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.width(4.dp))

        Button(symbol = "+", onClick = { onPlusClick() })
    }
}

@Composable
private fun Button(
    modifier: Modifier = Modifier,
    symbol: String,
    onClick: () -> Unit,
    isActive: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = if (isActive) Color.Black else Color.DarkGray,
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = symbol,
            color = if (isActive) Color.Black else Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = modifier.padding(4.dp),
        )
    }
}
