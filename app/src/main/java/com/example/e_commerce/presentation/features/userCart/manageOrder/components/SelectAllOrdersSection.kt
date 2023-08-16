package com.example.e_commerce.presentation.features.userCart.manageOrder.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R

@Composable
fun SelectAllOrdersSection(
    modifier: Modifier = Modifier,
    numOfItems: Int,
    isChecked: Boolean = false,
    onCheckChange: () -> Unit,
    onDeleteSelectedItemsClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckChange()
            },
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "${stringResource(id = R.string.select_all_orders)}($numOfItems)",
            color = Color.DarkGray,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.delete),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .clickable {
                    onDeleteSelectedItemsClick()
                },
        )
    }
}
