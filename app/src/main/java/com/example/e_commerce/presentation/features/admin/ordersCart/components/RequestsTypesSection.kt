package com.example.e_commerce.presentation.features.admin.ordersCart.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RequestsTypesSection(
    modifier: Modifier = Modifier,
    requests: List<String> = listOf("Waiting", "Accepted", "Rejected", "Delivered"),
    selectedButton: String,
    onButtonSelected: (String) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        maxItemsInEachRow = 3,
    ) {
        requests.forEach { button ->

            DefaultRadioButton(
                text = button,
                selected = selectedButton == button,
                onSelect = {
                    onButtonSelected(button)
                },
            )
        }
    }
}
