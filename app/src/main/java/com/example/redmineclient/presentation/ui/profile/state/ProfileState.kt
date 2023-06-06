package com.example.redmineclient.presentation.ui.profile.state

import com.example.redmineclient.domain.models.UserInfo
import com.example.redmineclient.domain.state.LoadingState

data class ProfileState(
    val userInfo: UserInfo? = null,
    val loadingState: LoadingState = LoadingState.Loading
)
