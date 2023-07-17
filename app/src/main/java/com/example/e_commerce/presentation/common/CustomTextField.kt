package com.example.e_commerce.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.e_commerce.ui.theme.Purple80

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth(),
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
    )
}
