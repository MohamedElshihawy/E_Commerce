package com.example.e_commerce.presentation.features.signUp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CustomButton
import com.example.e_commerce.presentation.common.components.CustomTextField
import com.example.e_commerce.presentation.common.components.loadingAnimations.DialogBoxLoading
import com.example.e_commerce.ui.theme.DarkSlateBlue
import com.example.e_commerce.ui.theme.Lavender
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = get(),
    navController: NavController,
) {
    val userRegistrationState by remember {
        viewModel.userRegistrationUpState
    }

    val signInProcessState by remember { viewModel.signingProcessState }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup_bg),
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
            val scrollState = rememberScrollState()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState),

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
                    placeHolder = "User Name",
                    value = userRegistrationState.userNameField.value,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvents.EnteredUserName(it))
                    },
                    showError = userRegistrationState.userNameField.showError,
                    errorMessage = stringResource(id = R.string.user_name_validation),
                    onTrailingIconClick = {
                        viewModel.onEvent(SignUpEvents.EnteredUserName(""))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    placeHolder = "Email",
                    value = userRegistrationState.userEmailField.value,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvents.EnteredEmail(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                    showError = userRegistrationState.userEmailField.showError,
                    errorMessage = stringResource(id = R.string.user_email_violation),
                    onTrailingIconClick = {
                        viewModel.onEvent(SignUpEvents.EnteredEmail(""))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    placeHolder = "Phone Number",
                    value = userRegistrationState.userPhoneField.value,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvents.EnteredPhoneNumber(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                    ),
                    onTrailingIconClick = {
                        viewModel.onEvent(SignUpEvents.EnteredPhoneNumber(""))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    placeHolder = "Password",
                    value = userRegistrationState.userPasswordField.value,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvents.EnteredPassword(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    showError = userRegistrationState.userPasswordField.showError,
                    errorMessage = stringResource(id = R.string.user_password_violation),
                    onTrailingIconClick = {
                        viewModel.onEvent(SignUpEvents.EnteredPassword(""))
                    },
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomButton(
                    buttonColors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = DarkSlateBlue,
                    ),
                    text = stringResource(id = R.string.sign_up),
                    onButtonClick = {
                        viewModel
                            .onEvent(
                                SignUpEvents.SignUp(
                                    userRegistration = userRegistrationState,
                                ),
                            )
                    },
                )
            }
        }
    }

    val context = LocalContext.current
    if (signInProcessState.isLoading) {
        DialogBoxLoading()
    }

    LaunchedEffect(key1 = signInProcessState.isError) {
        if (signInProcessState.isError.isNotEmpty()) {
            Toast.makeText(context, signInProcessState.isError, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = signInProcessState.isSuccess) {
        if (signInProcessState.isSuccess.isNotEmpty()) {
            Toast.makeText(context, signInProcessState.isSuccess, Toast.LENGTH_SHORT).show()
            delay(500)
            navController.navigate(Screen.SignIn.route)
            Log.e("TAG", "SignUpScreen: ${signInProcessState.isSuccess}")
        }
    }
}
