package com.example.redmineclient.user_sign_up

data class UserSignUpState (
    val login: String = "",
    val isLoginHintVisible: Boolean = false,
    val passwordFirst: String = "",
    val isPasswordFirstHintVisible: Boolean = false,
    val passwordLast: String = "",
    val isPasswordLastHintVisible: Boolean = false,
    val isTeamLead: Boolean = false
)