package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction.Companion.Search
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    isSearching: Boolean = false,
    searchText: String,
    onSearchValueChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    onSearchIconClick: () -> Unit,
    onLogOutIconClick: () -> Unit,

) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (isSearching) {
                SearchBar(
                    value = searchText,
                    onSearchIconClick = onSearchIconClick,
                    onSearchSubmit = {
                        onSearchSubmit(it)
                    },
                    onSearchValueChange = {
                        onSearchValueChange(it)
                    },
                )
            } else {
                AppTitleAndSearchIcon(
                    onSearchIconClick = onSearchIconClick,
                    onLogOutIconClick = onLogOutIconClick
                )
            }
        },
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        scrollBehavior = null,
    )
}

@Composable
fun AppTitleAndSearchIcon(
    modifier: Modifier = Modifier,
    onSearchIconClick: () -> Unit,
    onLogOutIconClick: () -> Unit,
) {
    Row(modifier) {
        Text(
            text = "eCommerce",
            fontSize = 32.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { onSearchIconClick() },
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(
            onClick = { onLogOutIconClick() },
        ) {
            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = "Log Out",
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onSearchIconClick: () -> Unit,
    onSearchValueChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier) {
        CustomTextField(
            placeHolder = "Search",
            value = value,
            onValueChange = {
                onSearchValueChange(it)
            },
            isSingleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchSubmit(value)
                keyboardController?.hide()
            }),
            onTrailingIconClick = onSearchIconClick,
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { onSearchIconClick() },
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Search",
            )
        }
    }
}
