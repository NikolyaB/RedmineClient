package com.example.redmineclient.presentation.ui.authentication.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.presentation.ui.authentication.state.AuthenticationState
import com.example.redmineclient.presentation.ui.authentication.viewModel.AuthenticationViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.qualifier.named

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(
    viewModelWrapper: StatefulViewModelWrapper<AuthenticationViewModel, AuthenticationState> = getViewModel(named("AuthenticationViewModel"))
) {
    val state = viewModelWrapper.state
    val scaffoldState = rememberScaffoldState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
        ) {
            Text(
                text = "Авторизация",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Логин") },
                singleLine = true,
                isError = state.value.isError,
                modifier = Modifier.padding(top = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = state.value.isError,
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }){
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            )

            Button(
                onClick = {
                    viewModelWrapper.viewModel.onLoginClick(userRequest = UserRequest(login = login,
                        password = password))
                },
                modifier = Modifier.padding(top = 16.dp)
            )
            {
                Text(
                    text = "Войти"
                )
            }

            if (state.value.isError) {
                scope.launch {
                    state.value.messageError?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it, actionLabel = "Ок")
                        state.value.isError = false
                    }
                }
            }
        }
    }
}