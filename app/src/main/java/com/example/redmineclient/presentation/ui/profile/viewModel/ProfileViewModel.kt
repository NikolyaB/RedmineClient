package com.example.redmineclient.presentation.ui.profile.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.usecase.user.UserUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.authentication.state.AuthenticationState
import com.example.redmineclient.presentation.ui.authentication.viewModel.AuthenticationViewModel
import com.example.redmineclient.presentation.ui.profile.state.ProfileState
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface ProfileViewModel: StatefulViewModel<ProfileState> {
    override val state: StateFlow<ProfileState>
    val preferencesStore: PreferencesStore
    val userUseCase: UserUseCase
    fun getCurrentUser()
    fun onCurrentUserRefresh()
    fun logOut()
}

class ProfileViewModelImpl(
    private val navigator: Navigator,
    override val userUseCase: UserUseCase,
    override val preferencesStore: PreferencesStore
): KoinComponent, StatefulViewModelImpl<ProfileState>(), ProfileViewModel {
    private val mutableState = MutableStateFlow(
        ProfileState()
    )

    override val state: StateFlow<ProfileState>
        get() = mutableState

    override fun onViewShown() {
        super.onViewShown()
        getCurrentUser()
    }

    override fun getCurrentUser() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { key ->
                    val response = userUseCase.getCurrentUser(apiKey = key)
                    val data = response.userResponse?.user

                    when (response.statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success,
                                    userInfo = data
                                )
                            }
                            println(data)
                        }
                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun onCurrentUserRefresh() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        loadingState = LoadingState.Loading,
                    )
                }
                getCurrentUser()
            })
        })
    }

    override fun logOut() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.deleteToken()
            })
        })
        navigator.navigateToAuthentication()
    }


}