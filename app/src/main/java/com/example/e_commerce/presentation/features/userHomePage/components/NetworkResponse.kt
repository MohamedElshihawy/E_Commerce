package com.example.e_commerce.presentation.features.userHomePage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R

@Composable
fun NetworkError(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.no_data,
            ),
            contentDescription = "No data is found",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(
                id = R.string.something_went_wrong_try_again_later,
            ),
            style = TextStyle(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .fillMaxWidth(.8f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevnet() {
    NetworkError(onClick = {})
}
