package com.example.redmineclient.user_sign_in

data class UserSignInState (
    val userId: Long = -1L,
    val login: String = "",
    val isLoginHintVisible: Boolean = false,
    val password: String = "",
    val isPasswordHintVisible: Boolean = false
)

