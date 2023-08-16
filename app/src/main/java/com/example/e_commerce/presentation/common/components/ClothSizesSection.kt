package com.example.e_commerce.presentation.common.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SizesSection(
    modifier: Modifier = Modifier,
    allSizes: List<String> = listOf("S", "M", "L", "XL", "XXL"),
    productSelectedSizes: List<String>,
    showAllSizes: Boolean = true,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround, // Add spacing between items
    ) {
        allSizes.forEach { size ->
            Text(
                text = size,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                color = if (productSelectedSizes.contains(size)) Color.Blue else Color.Black,
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable {
                        onClick(size)
                    }
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = if (productSelectedSizes.contains(size)) Color.Blue else Color.Transparent,
                    )
                    .padding(8.dp),
            )
        }
    }
}
