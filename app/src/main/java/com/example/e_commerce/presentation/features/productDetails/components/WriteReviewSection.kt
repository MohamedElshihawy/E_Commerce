package com.example.e_commerce.presentation.features.productDetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.CustomTextField
import com.example.e_commerce.presentation.common.components.RatingStars

@Composable
fun WriteReviewSection(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean = true,
    reviewText: String,
    rating: Float,
    onButtonClick: () -> Unit,
    onReviewTextChange: (String) -> Unit,
    onRatingChanged: (Int) -> Unit,
    onTrailingIconClick: () -> Unit,
    onReviewSubmitted: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if (isCollapsed) {
            WriteReviewButton(
                onButtonClick = {
                    onButtonClick()
                },
            )
        } else {
            ReviewAndRating(
                onReviewSubmitted = onReviewSubmitted,
                onReviewTextChange = {
                    onReviewTextChange(it)
                },
                onRatingChanged = { onRatingChanged(it) },
                reviewText = reviewText,
                onTrailingIconClick = { onTrailingIconClick() },
                rating = rating,
            )
        }
    }
}

@Composable
fun WriteReviewButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable {
                onButtonClick()
            },
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Write Review",
                modifier = Modifier.size(36.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "Write a review", fontSize = 24.sp)

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ReviewAndRating(
    modifier: Modifier = Modifier,
    rating: Float = 0f,
    reviewText: String,
    onReviewTextChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onRatingChanged: (Int) -> Unit,
    onReviewSubmitted: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(text = "Rate us?")

        RatingStars(
            rating = rating,
            onClick = { onRatingChanged(it) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeHolder = "Enter Review",
            value = reviewText,
            onValueChange = {
                onReviewTextChange(it)
            },
            onTrailingIconClick = { onTrailingIconClick() },
            isSingleLine = false,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Submit",
        ) {
            onReviewSubmitted()
        }
    }
}
