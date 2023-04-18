package com.example.redmineclient.user_sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redmineclient.common_ui.TransparentHintTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserSignUpScreen(
    navController: NavController,
    viewModel: UserSignUpViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val hasUserBeenSaved by viewModel.hasUserBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasUserBeenSaved) {
        if (hasUserBeenSaved) {
            navController.popBackStack()
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
                        text = state.passwordFirst,
                        hint = "Пароль",
                        isHintVisible = state.isPasswordFirstHintVisible,
                        onValueChanged = viewModel::onPasswordFirstChanged,
                        onFocusChanged = {
                            viewModel.onPasswordFirstFocusChanged(it.isFocused)
                        },
                        singleLine = false,
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.width(220.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    TransparentHintTextField(
                        text = state.passwordLast,
                        hint = "Повторите пароль",
                        isHintVisible = state.isPasswordLastHintVisible,
                        onValueChanged = viewModel::onPasswordLastChanged,
                        onFocusChanged = {
                            viewModel.onPasswordLastFocusChanged(it.isFocused)
                        },
                        singleLine = false,
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.width(220.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row {
                        Text(
                            text = "Лидер команды:",
                            fontWeight = FontWeight.Light
                        )
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Change status user",
                            modifier = Modifier
                                .clickable(MutableInteractionSource(), null) {
                                    viewModel.isTeamLead(!state.isTeamLead)
                                }
                                .padding(horizontal = 6.dp),
                            tint = if (state.isTeamLead) Color.Green else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = viewModel::userSignUp,
                        modifier = Modifier.width(200.dp)) {
                        Text("Add user")
                    }
                }
            }
        }
    }
}