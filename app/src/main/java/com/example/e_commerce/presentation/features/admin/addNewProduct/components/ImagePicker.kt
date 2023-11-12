package com.example.e_commerce.presentation.features.admin.addNewProduct.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    pickedUri: String? = null,
    onImagePicked: (Uri?) -> Unit,
    icon: ImageVector = Icons.Outlined.ImageSearch,
    isClickable: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        if (uri != null) {
            onImagePicked(uri)
        }
    }

    if (pickedUri == null) {
        Image(
            imageVector = icon,
            contentDescription = "Image Picker",
            modifier = modifier
                .size(200.dp)
                .clip(shape)
                .clickable {
                    if (isClickable) {
                        pickImageLauncher.launch("image/*")
                    }
                },
        )
    } else {
        val painter = rememberAsyncImagePainter(model = pickedUri)

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painter,
            contentDescription = "Selected Image",
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .aspectRatio(1f)
                .clip(shape)
                .clickable {
                    if (isClickable) {
                        pickImageLauncher.launch("image/*")
                    }
                },
            contentScale = ContentScale.FillHeight,
        )
    }
}
