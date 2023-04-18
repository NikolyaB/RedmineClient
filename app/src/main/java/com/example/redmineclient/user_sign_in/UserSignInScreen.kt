package com.example.redmineclient.user_sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redmineclient.common_ui.TransparentHintTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserSignInScreen(
    navController: NavController,
    viewModel: UserSignInViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val hasUserBeenSignIn by viewModel.hasUserBeenSignIn.collectAsState()

    LaunchedEffect(key1 = hasUserBeenSignIn) {
        if (hasUserBeenSignIn) {
            navController.navigate("task_list/${state.userId}")
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    TransparentHintTextField(
                        text = state.login,
                        hint = "Логин",
                        isHintVisible = state.isLoginHintVisible,
                        onValueChanged = viewModel::onLoginChanged,
                        onFocusChanged = {
                            viewModel.onLoginFocusChanged(it.isFocused)
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.width(220.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    TransparentHintTextField(
                        text = state.password,
                        hint = "Пароль",
                        isHintVisible = state.isPasswordHintVisible,
                        onValueChanged = viewModel::onPasswordChanged,
                        onFocusChanged = {
                            viewModel.onPasswordFocusChanged(it.isFocused)
                        },
                        singleLine = false,
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.width(220.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = viewModel::userSignIn,
                        modifier = Modifier.width(200.dp)) {
                        Text("SignIn")
                    }
                }
            }
        }
    }
}