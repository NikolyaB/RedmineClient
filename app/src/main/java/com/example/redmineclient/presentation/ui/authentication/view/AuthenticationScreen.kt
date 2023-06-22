package com.example.redmineclient.presentation.ui.authentication.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.WhiteDark
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
    var login by remember { mutableStateOf("test.test") }
    var password by remember { mutableStateOf("SWtUi5OoRxlvQDj7") }
    var isFocusedLogin by remember { mutableStateOf(false) }
    var isFocusedPassword by remember { mutableStateOf(false) }


    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        backgroundColor = WhiteDark
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(BlueLight)
            ) {
                Text(
                    text = "Авторизация",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = BlueDark,
                    textAlign = TextAlign.Center,
                )
            }
            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Логин") },
                singleLine = true,
                isError = state.value.isError,
                modifier = Modifier.padding(top = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueDark
                )
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

                    val description = if (passwordVisible) "" else ""

                    IconButton(onClick = { passwordVisible = !passwordVisible }){
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueDark
                ),
                )

            Button(
                onClick = {
                    viewModelWrapper.viewModel.onLoginClick(userRequest = UserRequest(login = login,
                        password = password))
                },
                modifier = Modifier
                    .padding(top = 36.dp)
                    .width(130.dp),
                shape = RoundedCornerShape(18),
                colors = ButtonDefaults.buttonColors(Blue)
            )
            {
                Text(
                    text = "Войти",
                    color = Color.White
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