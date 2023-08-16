package com.example.e_commerce.presentation.features.productDetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CollapsableItem(
    modifier: Modifier = Modifier,
    text: String,
    isCollapsed: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (isCollapsed) {
                    Icons.Default.ArrowForwardIos
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = "Collapse",

            )
        }
    }

    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Gray),
    )

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = !isCollapsed) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun collapse() {
    CollapsableItem(text = "Product Reviews", onClick = {}) {
    }
}
