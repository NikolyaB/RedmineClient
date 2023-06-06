package com.example.redmineclient.presentation.ui.mainNavigation.viewModel

import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

interface MainNavigationViewModel: StatefulViewModel<MainNavigationState> {
    val startDestination: ScreenRoute
    override val state: StateFlow<MainNavigationState>
    fun onRouteChange(route: ScreenRoute)
}

class MainNavigationViewModelImpl(
    override val startDestination: ScreenRoute
): KoinComponent, StatefulViewModelImpl<MainNavigationState>(), MainNavigationViewModel {

    private val mutableState = MutableStateFlow(
        MainNavigationState(startDestination)
    )

    override val state: StateFlow<MainNavigationState>
        get() = mutableState

    override fun onRouteChange(route: ScreenRoute) {
        if (route.isMain) mutableState.update { it.copy(screenRoute = route) }
    }
}