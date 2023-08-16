package com.example.e_commerce.presentation.features.productDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.presentation.common.components.RatingStars

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    averageRating: Float,
    numOfVotes: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = averageRating.toString(),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            RatingStars(
                rating = averageRating,
            ) { rating ->
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$numOfVotes Reviews",
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingS() {
    RatingSection(averageRating = 4.6f, numOfVotes = 54)
}
