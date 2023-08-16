package com.example.e_commerce.presentation.features.splash

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.ui.theme.DarkSlateBlue
import com.example.e_commerce.ui.theme.darkOrange
import org.koin.androidx.compose.get

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = get(),
    navController: NavController,

) {
    val signInState = remember {
        viewModel.signInState
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkSlateBlue),
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Buy \nany thing\nyou need whenever you want online",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column(modifier = Modifier) {
                CustomButton(
                    buttonColors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = DarkSlateBlue,
                    ),
                    text = stringResource(id = R.string.join_now),
                    modifier = Modifier,
                    onButtonClick = {
                        navController.navigate(Screen.SignUp.route)
                    },
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomButton(
                    buttonColors = ButtonDefaults.buttonColors(
                        containerColor = darkOrange,
                        contentColor = Color.White,
                    ),
                    text = "${
                        stringResource(
                            id = R.string.already_have_an_account,
                        )
                    } ${
                        stringResource(
                            id = R.string.sign_in,
                        )
                    }",
                    modifier = Modifier,
                    onButtonClick = {
                        navController.navigate(Screen.SignIn.route)
                    },
                )
            }
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = signInState.value.isSuccess) {
        Log.e("TAG", "SplashScreen: success")
        if (signInState.value.isSuccess != "") {
            Toast.makeText(context, signInState.value.isSuccess, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.UserHomeScreen.route)
        }
        // navController.navigate(Screen.AdminCategoriesManagement.route)
        // navController.navigate(Screen.UserHomeScreen.route)
    }

    LaunchedEffect(key1 = signInState.value.isError) {
        if (signInState.value.isError != "") {
            Toast.makeText(context, signInState.value.isError, Toast.LENGTH_SHORT).show()
        }
    }
}
