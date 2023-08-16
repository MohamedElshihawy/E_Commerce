package com.example.e_commerce.presentation.features.userCart.manageOrder.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.presentation.common.components.ImageFromLink

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    userOrder: UserOrder,
    counterValue: Int,
    onDeleteClick: (String) -> Unit,
    onPlusClick: (String) -> Unit,
    onMinusClick: (String) -> Unit,
    onItemSelectedListener: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if (!isSelected) onItemSelectedListener() else return@detectTapGestures
                    },
                )
            },
    ) {
        AnimatedVisibility(visible = isSelected) {
            if (isSelected) {
                Checkbox(checked = isSelected, onCheckedChange = { onItemSelectedListener() })
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        ImageFromLink(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(.7f),
            imageUrl = userOrder.image,
        )

        Spacer(modifier = Modifier.weight(1f))

        OrderDetailsSection(
            userOrder = userOrder,
            counterValue = counterValue,
            onDeleteClick = onDeleteClick,
            onPlusClick = { onPlusClick(it) },
            onMinusClick = { onMinusClick(it) },
            modifier = Modifier.padding(end = 8.dp),
        )
    }
}

@Composable
fun OrderDetailsSection(
    modifier: Modifier = Modifier,
    userOrder: UserOrder,
    counterValue: Int,
    onDeleteClick: (String) -> Unit,
    onPlusClick: (String) -> Unit,
    onMinusClick: (String) -> Unit,
) {
    Box(
        modifier = modifier,
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = userOrder.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                onDeleteClick(userOrder.productId)
            }) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete Order",
                    tint = Color.DarkGray,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Unit:$${userOrder.unitPrice}", color = Color.DarkGray)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "total:$${userOrder.totalPrice}", color = Color.DarkGray)

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Counter(
                count = counterValue,
                onPlusClick = { onPlusClick(userOrder.date) },
                onMinusClick = { onMinusClick(userOrder.date) },
            )
        }
    }
}
