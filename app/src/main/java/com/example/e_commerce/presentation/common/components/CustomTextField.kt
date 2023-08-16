package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.ui.theme.DarkSlateBlue
import com.example.e_commerce.ui.theme.Purple80

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showError: Boolean = false,
    errorMessage: String = "Please review your input",
    isSingleLine: Boolean = true,
    isEnabled: Boolean = true,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(roundedCornerShape),
    ) {
        TextField(
            value = value,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = DarkSlateBlue,
                    shape = RoundedCornerShape(8.dp),
                ),
            singleLine = isSingleLine,
            placeholder = {
                Text(text = placeHolder)
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                disabledLabelColor = Purple80,
            ),
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            isError = showError,
            trailingIcon = {
                if (isEnabled && value.isNotEmpty()) {
                    IconButton(
                        onClick = onTrailingIconClick,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close or clear",
                        )
                    }
                }
            },
            enabled = isEnabled,
        )
    }

    if (showError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Preview
@Composable
fun prevField() {
    CustomTextField(placeHolder = "Text", value = "", onValueChange = {}, onTrailingIconClick = {})
}
