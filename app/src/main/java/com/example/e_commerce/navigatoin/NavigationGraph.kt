package com.example.e_commerce.navigatoin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.presentation.common.components.NavDrawer
import com.example.e_commerce.presentation.features.addNewProduct.AddNewProductScreen
import com.example.e_commerce.presentation.features.adminCategoriesManagement.AdminCategoriesScreen
import com.example.e_commerce.presentation.features.manageUserProfile.SettingsScreen
import com.example.e_commerce.presentation.features.productDetails.ProductDetailsScreen
import com.example.e_commerce.presentation.features.signIn.SignInScreen
import com.example.e_commerce.presentation.features.signUp.SignUpScreen
import com.example.e_commerce.presentation.features.splash.SplashScreen
import com.example.e_commerce.presentation.features.userCart.confirmOrder.ConfirmOrderScreen
import com.example.e_commerce.presentation.features.userCart.manageOrder.CartScreen
import com.example.e_commerce.presentation.features.userHomePage.HomeScreen
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.LAST_SCROLL_POSITION
import com.example.e_commerce.util.Constants.PRODUCT_ID
import kotlinx.coroutines.launch

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val backStack = navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val userName = remember {
        mutableStateOf("")
    }
    val showDrawer = remember {
        mutableStateOf(false)
    }

    val showFloatingButton = remember {
        mutableStateOf(false)
    }

    val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.route!!.contains(Screen.UserHomeScreen.route)) {
                showFloatingButton.value = true
                showDrawer.value = true
            } else {
                showDrawer.value = false
                showFloatingButton.value = false
            }
        }

    navController.addOnDestinationChangedListener(
        listener = listener,
    )

    LaunchedEffect(key1 = true) {
        PreferenceDataStoreHelper(context)
            .apply {
                getPreference(
                    key = PreferenceDataStoreConstants.USER_NAME_KEY,
                    defaultValue = "",
                ).collect { userNameFlow ->
                    userName.value = userNameFlow
                }
            }
    }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            if (showDrawer.value) {
                NavDrawer(
                    modifier = Modifier
                        .width(350.dp),
                    onItemClick = { item ->
                        navigateToDestination(item, navController)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    userName = userName.value,
                )
            }
        },
        content = {
            Scaffold(
                floatingActionButton = {
                    if (showFloatingButton.value) {
                        FloatingActionButton(
                            onClick = { navController.navigate(Screen.CartScreen.route) },
                            shape = CircleShape,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Shopping cart",
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                },
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding),
                ) {
                    AppNavGraph(navController, drawerState)
                }
            }
        },
    )
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(route = Screen.AdminCategoriesManagement.route) {
            AdminCategoriesScreen(navController = navController)
        }

        composable(
            route = Screen.AdminAddsNewProduct.route + "/{${Constants.SELECTED_CATEGORY}}",
            arguments = listOf(
                navArgument(Constants.SELECTED_CATEGORY) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { backStack ->
            val categoryName =
                backStack.arguments?.getString(Constants.SELECTED_CATEGORY) ?: "Nothing"
            AddNewProductScreen(categoryName = categoryName)
        }

        composable(
            route = "${Screen.UserHomeScreen.route}?$LAST_SCROLL_POSITION={$LAST_SCROLL_POSITION}",
        ) { backStack ->

            val scrollPosition =
                backStack.arguments?.getString(LAST_SCROLL_POSITION)?.toIntOrNull() ?: 0
            HomeScreen(
                drawerState = drawerState,
                navController = navController,
                startScrollPosition = scrollPosition,
            )
        }

        composable(route = Screen.UserProfileScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(
            route = "${Screen.ProductDetails.route}/{$PRODUCT_ID}/{$LAST_SCROLL_POSITION}",
            arguments = listOf(
                navArgument(PRODUCT_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(LAST_SCROLL_POSITION) {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
        ) { backStack ->

            val productId = backStack.arguments!!.getString(PRODUCT_ID)
            val scrollPosition = backStack.arguments!!.getInt(LAST_SCROLL_POSITION)
            ProductDetailsScreen(
                productId = productId!!,
                navController = navController,
                itemPosition = scrollPosition,
            )
        }

        composable(
            route = Screen.CartScreen.route,
        ) {
            CartScreen(navController = navController)
        }

        composable(
            route = Screen.ConfirmOrderCartScreen.route,
        ) {
            ConfirmOrderScreen(navController = navController)
        }
    }
}

private fun showNavDrawer(backStack: NavBackStackEntry?): Boolean {
    return when (backStack?.destination?.route) {
        Screen.UserHomeScreen.route -> {
            true
        }

        else -> {
            false
        }
    }
}

private fun navigateToDestination(destination: String, navController: NavController) {
    if (destination == "Manage Profile") {
        navController.navigate(Screen.UserProfileScreen.route)
    }
}
