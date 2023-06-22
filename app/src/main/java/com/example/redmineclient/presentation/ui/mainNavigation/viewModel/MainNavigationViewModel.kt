package com.example.redmineclient.presentation.ui.mainNavigation.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface MainNavigationViewModel: StatefulViewModel<MainNavigationState> {
    val startDestination: ScreenRoute
    val preferencesStore: PreferencesStore
    override val state: StateFlow<MainNavigationState>
    fun onRouteChange(route: ScreenRoute)
}

class MainNavigationViewModelImpl(
    override val startDestination: ScreenRoute,
    override val preferencesStore: PreferencesStore
): KoinComponent, StatefulViewModelImpl<MainNavigationState>(), MainNavigationViewModel {

    private val mutableState = MutableStateFlow(
        MainNavigationState()
    )

    override val state: StateFlow<MainNavigationState>
        get() = mutableState
    override fun onViewShown() {
        super.onViewShown()
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { key ->
                    if (key != "") {
                        onRouteChange(ScreenRoute.Projects)
                        mutableState.update {
                            it.copy(
                                loadingState = LoadingState.Success
                            )
                        }
                    }
                    else {
                        onRouteChange(ScreenRoute.Authentication)
                        mutableState.update {
                            it.copy(
                                loadingState = LoadingState.Success
                            )
                        }
                    }
                }
            })
        })
    }
    override fun onRouteChange(route: ScreenRoute) {
        if (route.isMain) mutableState.update { it.copy(screenRoute = route) }
    }
}