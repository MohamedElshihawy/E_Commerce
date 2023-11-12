package com.example.e_commerce.navigatoin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_commerce.presentation.common.components.BottomNavBar
import com.example.e_commerce.presentation.features.admin.addNewProduct.AddNewProductScreen
import com.example.e_commerce.presentation.features.admin.adminCategoriesManagement.AdminCategoriesScreen
import com.example.e_commerce.presentation.features.admin.ordersCart.AdminOrdersCartScreen
import com.example.e_commerce.presentation.features.shared.signIn.SignInScreen
import com.example.e_commerce.presentation.features.shared.signUp.SignUpScreen
import com.example.e_commerce.presentation.features.shared.splash.SplashScreen
import com.example.e_commerce.presentation.features.user.manageUserProfile.SettingsScreen
import com.example.e_commerce.presentation.features.user.productDetails.ProductDetailsScreen
import com.example.e_commerce.presentation.features.user.userCart.confirmOrder.ConfirmOrderScreen
import com.example.e_commerce.presentation.features.user.userCart.manageOrder.CartScreen
import com.example.e_commerce.presentation.features.user.userHomePage.HomeScreen
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.LAST_SCROLL_POSITION
import com.example.e_commerce.util.Constants.PRODUCT_ID
import org.koin.androidx.compose.get

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val viewModel: NavigationViewModel = get()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val userName = viewModel.userName.collectAsState()
    val userAuthority = viewModel.userAuthority.collectAsState()

    val showBottomNavigationBar = remember {
        mutableStateOf(false)
    }

    val showFloatingButton = remember {
        mutableStateOf(false)
    }

    val listener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.route!! == Screen.UserHomeScreen.route) {
                showFloatingButton.value = true
                showBottomNavigationBar.value = true
            } else if (
                destination.route!! == Screen.Splash.route ||
                destination.route!! == Screen.SignUp.route ||
                destination.route!! == Screen.SignIn.route
            ) {
                showBottomNavigationBar.value = false
                showFloatingButton.value = false
            } else {
                showBottomNavigationBar.value = true
                showFloatingButton.value = false
            }
        }

    navController.addOnDestinationChangedListener(
        listener = listener,
    )
    Scaffold(
        floatingActionButton = {
            if (showFloatingButton.value) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.UserCartScreen.route) },
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
        bottomBar = {
            if (showBottomNavigationBar.value) {
                BottomNavBar(userAuthority = userAuthority.value, navController = navController)
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
            route = Screen.UserCartScreen.route,
        ) {
            CartScreen(navController = navController)
        }

        composable(
            route = Screen.ConfirmOrderCartScreen.route,
        ) {
            ConfirmOrderScreen(navController = navController)
        }

        composable(route = Screen.AdminOrdersCartScreen.route) {
            AdminOrdersCartScreen(
                navController = navController,
            )
        }
    }
}
