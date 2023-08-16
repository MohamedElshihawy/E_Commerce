package com.example.e_commerce.presentation.common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.e_commerce.presentation.common.components.loadingAnimations.CircleLoadingAnimation
import kotlin.math.min

@Composable
fun ImageFromLink(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .build(),
    )

    val state = painter.state

    val transition by animateFloatAsState(
        targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
        label = "",
    )

    if (state is AsyncImagePainter.State.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize(),
        ) {
            CircleLoadingAnimation()
        }
    }

    Image(
        painter = painter,
        contentDescription = "Product Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .scale(.8f + (.2f * transition))
            .graphicsLayer { rotationX = (1f - transition) * 5f }
            .alpha(min(1f, transition / .2f)),
    )
}
