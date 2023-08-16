package com.example.e_commerce.presentation.features.manageUserProfile

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigatoin.Screen
import com.example.e_commerce.presentation.common.components.CustomTextField
import com.example.e_commerce.presentation.common.components.loadingAnimations.DialogBoxLoading
import com.example.e_commerce.presentation.features.addNewProduct.components.ImagePicker
import com.example.e_commerce.presentation.features.manageUserProfile.components.UserProfileTopTopBar
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageUserProfileViewModel = get(),
    navController: NavController,
) {
    val nameState = viewModel.userNameState
    val emailSate = viewModel.userEmailState
    val passwordState = viewModel.userPasswordState
    val addressState = viewModel.userAddressState
    val phoneState = viewModel.userPhoneState
    val profileImageState = viewModel.userImageState
    val editModeState = viewModel.enableEditMode
    val editOrSaveUserState = viewModel.editOrSaveUserOption
    val networkRequestState = viewModel.modifyUserNetworkRequestState

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column {
            UserProfileTopTopBar(
                title = R.string.settings,
                onBackIconClick = {
                    navController.navigate(Screen.UserHomeScreen.route)
                },
                onTextClick = {
                    if (it == "edit") {
                        viewModel.onEvent(
                            ManageUserProfileScreenEvents.EditUser,
                        )
                    } else {
                        viewModel.onEvent(
                            ManageUserProfileScreenEvents.SaveUserModification,
                        )
                    }
                },
                editOrSaveText = editOrSaveUserState.value,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ImagePicker(
                    icon = Icons.Outlined.AccountCircle,
                    onImagePicked = { imageUri ->
                        Log.e("TAG", "SettingsScreen: picked $imageUri" )
                        viewModel
                            .onEvent(
                                ManageUserProfileScreenEvents
                                    .PickedProfileImage(imageUri),
                            )
                    },
                    pickedUri = profileImageState.value.toString(),
                    shape = CircleShape,
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    placeHolder = stringResource(
                        id = R.string.user_name,
                    ),
                    value = nameState.value.value,
                    onValueChange = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredUserName(it))
                    },
                    onTrailingIconClick = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredUserName(""))
                    },
                    showError = nameState.value.showError,
                    isEnabled = editModeState.value,
                    errorMessage = stringResource(
                        id = R.string.user_name_validation,
                    ),
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    placeHolder = stringResource(
                        id = R.string.user_email,
                    ),
                    value = emailSate.value.value,
                    onValueChange = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredEmail(it))
                    },
                    onTrailingIconClick = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredEmail(""))
                    },
                    showError = emailSate.value.showError,
                    isEnabled = editModeState.value,
                    errorMessage = stringResource(
                        id = R.string.user_email_violation,
                    ),
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    placeHolder = stringResource(
                        id = R.string.user_phone,
                    ),
                    value = phoneState.value.value,
                    onValueChange = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredPhoneNumber(it))
                    },
                    onTrailingIconClick = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredPhoneNumber(""))
                    },
                    showError = emailSate.value.showError,
                    isEnabled = editModeState.value,
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    placeHolder = stringResource(
                        id = R.string.user_address,
                    ),
                    value = addressState.value.value,
                    onValueChange = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredAddress(it))
                    },
                    onTrailingIconClick = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredAddress(""))
                    },
                    showError = emailSate.value.showError,
                    isEnabled = editModeState.value,
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    placeHolder = stringResource(
                        id = R.string.user_password,
                    ),
                    value = passwordState.value.value,
                    onValueChange = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredPassword(it))
                    },
                    onTrailingIconClick = {
                        viewModel
                            .onEvent(ManageUserProfileScreenEvents.EnteredPassword(""))
                    },
                    showError = passwordState.value.showError,
                    isEnabled = editModeState.value,
                    errorMessage = stringResource(
                        id = R.string.user_password_violation,
                    ),
                )
            }
        }

        val context = LocalContext.current

        if (networkRequestState.value.isLoading) {
            DialogBoxLoading()
        }
//
//        LaunchedEffect(key1 = networkRequestState.value.isError) {
//            if (networkRequestState.value.isError.isNotEmpty()) {
//                Toast.makeText(context, "Couldn't update user profile", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        LaunchedEffect(key1 = networkRequestState.value.isSuccess) {
//            if (networkRequestState.value.isSuccess.isNotEmpty()) {
//                Toast.makeText(context, "Updated user profile Successfully", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
    }
}
