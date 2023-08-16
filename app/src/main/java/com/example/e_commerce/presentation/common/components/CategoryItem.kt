package com.example.e_commerce.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    name: String,
    contentDescription: String = "",
    itemShape: Shape = RoundedCornerShape(8.dp),
    onClick: (String) -> Unit,
) {
    Surface(
        shadowElevation = 4.dp,
        shape = itemShape,
        modifier = modifier
            .padding(8.dp).clickable {
                onClick(name)
            },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CategoryIcon(
                icon = icon,
                contentDescription = contentDescription,
            )
            CategoryName(text = name)
        }
    }
}

@Composable
private fun CategoryIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String = "",

) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        modifier = modifier
            .size(64.dp)
            .padding(vertical = 8.dp),
    )
}

@Composable
private fun CategoryName(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        modifier = modifier
            .padding(4.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun prevItem() {
    CategoryName(text = "Gems")
}

@Preview(showBackground = true)
@Composable
fun prevIcon() {
    CategoryIcon(icon = R.drawable.icon_food)
}

@Preview(showBackground = true)
@Composable
fun previtem() {
    CategoryItem(icon = R.drawable.icon_food, name = "Food", onClick = {
    })
}
