package com.example.e_commerce.presentation.splash

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R
import com.example.e_commerce.presentation.common.CustomButton
import com.example.e_commerce.ui.theme.SplashBG
import com.example.e_commerce.ui.theme.darkOrange

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SplashBG),
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
                        contentColor = SplashBG,
                    ),
                    text = stringResource(id = R.string.join_now),
                    modifier = Modifier,
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
                )
            }
        }
    }
}

@Preview
@Composable
fun prev() {
    SplashScreen()
}
