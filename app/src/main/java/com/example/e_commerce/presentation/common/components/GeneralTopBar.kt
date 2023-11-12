package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneralTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Surface(
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "Back button",
                )
            }
        }
    }
}
