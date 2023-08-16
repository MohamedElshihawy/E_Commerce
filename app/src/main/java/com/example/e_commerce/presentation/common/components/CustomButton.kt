package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    shape: Shape = RoundedCornerShape(8.dp),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onButtonClick: () -> Unit,
) {
    Button(
        onClick = { onButtonClick() },
        shape = shape,
        colors = buttonColors,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                shape = shape,
                elevation = 8.dp,
            ),
    ) {
        Row(modifier = Modifier) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}
