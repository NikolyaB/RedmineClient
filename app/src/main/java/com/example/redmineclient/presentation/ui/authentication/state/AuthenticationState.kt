package com.example.redmineclient.presentation.ui.authentication.state

import com.example.redmineclient.domain.state.StatusResponse

data class AuthenticationState (
    val statusResponse: StatusResponse? = null,
    var isError: Boolean = false,
    var messageError: String? = null,
)
