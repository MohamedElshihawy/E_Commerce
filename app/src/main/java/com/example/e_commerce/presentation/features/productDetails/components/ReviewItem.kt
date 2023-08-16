package com.example.e_commerce.presentation.features.productDetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.presentation.common.components.RatingStars

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: ProductReviews,
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Box(
        modifier
            .widthIn(max = 300.dp)
            .heightIn(min = 160.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .clickable {
                isExpanded.value = !isExpanded.value
            },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 28.dp),
        ) {
            RatingStars(rating = review.rating.toFloat()) {}

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.comment,
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = if (isExpanded.value) Int.MAX_VALUE else 3,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize(),
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = review.userName,
                fontSize = 12.sp,
            )

            Text(
                text = review.date,
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun pReview() {
    ReviewItem(review = ProductReviews(date = "7/8/2023"))
}
