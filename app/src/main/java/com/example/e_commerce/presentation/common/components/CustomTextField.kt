package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.e_commerce.ui.theme.DarkSlateBlue
import com.example.e_commerce.ui.theme.Purple80

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showError: Boolean = false,
    errorMessage: String = "Please review your input",
    onValueChange: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
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
            singleLine = true,
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
            visualTransformation = visualTransformation,
            isError = showError,
        )
    }

    if (showError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
