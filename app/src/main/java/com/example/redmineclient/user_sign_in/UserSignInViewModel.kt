package com.example.redmineclient.user_sign_in

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineclient.domain.user.User
import com.example.redmineclient.domain.user.UserDataSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserSignInViewModel(
    private val userDataSource: UserDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId = savedStateHandle.getStateFlow("userId", -1L)
    private val login = savedStateHandle.getStateFlow("login", "")
    private val isLoginFocused = savedStateHandle.getStateFlow("isLoginFocused", false)
    private val password = savedStateHandle.getStateFlow("password", "")
    private val isPasswordFocused = savedStateHandle.getStateFlow("isPasswordFocused", false)

    val state = combine(
        userId,
        login,
        isLoginFocused,
        password,
        isPasswordFocused,
    ) {  id, login, isLoginFocused, password, isPasswordFocused, ->
        UserSignInState(
            userId = id,
            login = login,
            isLoginHintVisible = login.isEmpty() && !isLoginFocused,
            password = password,
            isPasswordHintVisible = password.isEmpty() && !isPasswordFocused,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserSignInState())

    init {
        viewModelScope.launch {
            userDataSource.insertUser(
                User(
                    id = 1L,
                    login = "admin",
                    password = "admin",
                    isTeamLead = true
                )
            )
        }
    }

    private val _hasUserBeenSignIn = MutableStateFlow(false)
    val hasUserBeenSignIn = _hasUserBeenSignIn.asStateFlow()

    fun onLoginChanged(text: String) { savedStateHandle["login"] = text }

    fun onPasswordChanged(text: String) { savedStateHandle["password"] = text }

    fun onLoginFocusChanged(isFocused: Boolean) { savedStateHandle["isLoginFocused"] = isFocused }

    fun onPasswordFocusChanged(isFocused: Boolean) { savedStateHandle["isPasswordFocused"] = isFocused }


    fun userSignIn() {
        viewModelScope.launch {
            userDataSource.loginUser(login = login.value, password = password.value)?.let { user ->
                if (user.login == login.value && user.password == password.value ) {
                    savedStateHandle["userId"] = user.id
                    _hasUserBeenSignIn.value = true
                }
            }
        }
    }
}