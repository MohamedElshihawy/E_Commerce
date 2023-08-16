package com.example.e_commerce.presentation.features.userHomePage.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.domain.models.CategoryItemData
import com.example.e_commerce.domain.models.categoriesItemsList
import com.example.e_commerce.presentation.common.components.CategoryItem

@Composable
fun CategoriesList(
    modifier: Modifier = Modifier,
    itemsList: List<CategoryItemData> = categoriesItemsList,
    onItemClick: (String) -> Unit,

) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(itemsList) { item ->

            CategoryItem(
                icon = item.icon,
                name = item.name,
                contentDescription = item.contentDescription,
                onClick = onItemClick,
            )
            Spacer(Modifier.width(2.dp))
        }
    }
}

@Preview
@Composable
fun prevCategoryList() {
    CategoriesList(
        onItemClick = {},
    )
}
