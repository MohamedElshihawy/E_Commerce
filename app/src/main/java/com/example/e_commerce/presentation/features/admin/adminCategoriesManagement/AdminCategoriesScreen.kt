package com.example.e_commerce.presentation.features.admin.adminCategoriesManagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.domain.models.categoriesItemsList
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CategoryItem

@Composable
fun AdminCategoriesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scrollState = rememberLazyGridState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.add_new_product),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(
            modifier = Modifier.height(40.dp),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = scrollState,
            modifier = modifier,
        ) {
            items(categoriesItemsList) { category ->
                CategoryItem(
                    icon = category.icon,
                    name = category.name,
                    contentDescription = category.contentDescription,
                ) {
                    navController.navigate(Screen.AdminAddsNewProduct.addArgs(category.name))
                }
            }
        }
    }
}
