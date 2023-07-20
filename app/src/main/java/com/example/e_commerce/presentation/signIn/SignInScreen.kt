package com.example.e_commerce.presentation.signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.CustomTextField
import com.example.e_commerce.ui.theme.DarkSlateBlue
import com.example.e_commerce.ui.theme.Lavender
import org.koin.androidx.compose.get

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = get(),
) {
    val signInState by remember { viewModel.signInScreenState }

    val signInProcessState by remember { viewModel.signingProcessState }

    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.signin_bg),
            contentDescription = "BG",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f),
        )
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 40.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                )
                .background(Lavender),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = DarkSlateBlue,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp,
            ),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "eCommerce",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(36.dp))

                CustomTextField(
                    placeHolder = "Email",
                    value = signInState.userEmailField.value,
                    onValueChange = {
                        viewModel.onEvent(SignInEvents.EnteredEmail(it))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    placeHolder = "Password",
                    value = signInState.userPasswordField.value,
                    onValueChange = {
                        viewModel.onEvent(SignInEvents.EnteredPassword(it))
                    },
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Checkbox(
                        checked = signInState.checkBox,
                        onCheckedChange = {
                            viewModel.onEvent(
                                SignInEvents.RememberMe(!signInState.checkBox),
                            )
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = DarkSlateBlue,
                            uncheckedColor = DarkSlateBlue,
                        ),
                    )

                    Text(
                        text = stringResource(id = R.string.remember_me),
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { },
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        fontSize = 18.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.clickable { },
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                CustomButton(
                    buttonColors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = DarkSlateBlue,
                    ),
                    text = stringResource(id = R.string.sign_in),
                    onButtonClick = {
                        viewModel.onEvent(
                            SignInEvents.SignIn(
                                name = signInState.userEmailField.value,
                                password = signInState.userPasswordField.value,
                            ),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.iam_not_an_admin),
                        fontSize = 18.sp,
                        modifier = Modifier.clickable { },
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.iam_an_admin),
                        fontSize = 18.sp,
                        modifier = Modifier.clickable { },
                    )
                }
            }
        }
    }

    if (signInProcessState.isLoading) {
        CircularProgressIndicator()
    }

    LaunchedEffect(key1 = signInProcessState.isError) {
        if (signInProcessState.isError.isNotEmpty()) {
            Toast.makeText(context, signInProcessState.isError, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = signInProcessState.isSuccess) {
        if (signInProcessState.isSuccess.isNotEmpty()) {
            Toast.makeText(context, signInProcessState.isSuccess, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prev() {
    SignInScreen()
}
