package com.example.e_commerce.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce.domain.models.NavDrawerItem
import com.example.e_commerce.domain.models.navDrawerItems

@Composable
fun NavDrawer(
    modifier: Modifier = Modifier,
    items: List<NavDrawerItem> = navDrawerItems,
    userName: String = "User Name",
    userProfileImage: String? = "",
    onItemClick: (String) -> Unit,

) {
    val selectedItem = remember { mutableStateOf(items[0]) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        DrawerTopSection(
            userName = userName,
            userImage = userProfileImage,
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
        ) {
            items(items) { item ->
                NavDrawerItem(
                    item,
                    isSelected = item == selectedItem.value,
                ) {
                    onItemClick(item.text)
                    selectedItem.value = item
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DrawerTopSection(
    modifier: Modifier = Modifier,
    userName: String,
    userImage: String?,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserProfileImage(
                imageUrl = userImage,
                userName = userName,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userName,
                fontSize = 26.sp,
                fontWeight = Medium,
            )
        }
    }
}

@Composable
fun UserProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    userName: String,
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(shape = CircleShape)
            .shadow(2.dp, shape = CircleShape)
            .background(Color.White),
        contentAlignment = Center,
    ) {
        if (imageUrl != "") {
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
            )
        } else {
            Text(
                text = "userName.first().toString()",
                style = TextStyle(
                    fontSize = 60.sp,
                    fontWeight = Bold,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black,
                ),
            )
        }
    }
}

@Composable
fun NavDrawerItem(
    item: NavDrawerItem,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = item.text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = Medium,
                ),
            )
        },
        selected = isSelected,
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = item.contentDescription,
                modifier = modifier.size(36.dp),
            )
        },
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
fun prev() {
    NavDrawer(
        userName = "Mohamed",
        userProfileImage = "",
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp),
        items = navDrawerItems,
        onItemClick = { string ->
        },
    )
}
