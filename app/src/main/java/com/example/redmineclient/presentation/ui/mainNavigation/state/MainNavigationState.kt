package com.example.redmineclient.presentation.ui.mainNavigation.state

import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.navigator.ScreenRoute

data class MainNavigationState(
    val screenRoute: ScreenRoute = ScreenRoute.Authentication,
    val loadingState: LoadingState = LoadingState.Loading
)