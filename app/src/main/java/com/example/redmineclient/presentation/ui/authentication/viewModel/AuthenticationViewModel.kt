package com.example.redmineclient.presentation.ui.authentication.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.domain.usecase.user.UserUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.authentication.state.AuthenticationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface AuthenticationViewModel: StatefulViewModel<AuthenticationState> {
    override val state: StateFlow<AuthenticationState>
    val userUseCase: UserUseCase
    val preferencesStore: PreferencesStore
    fun onLoginClick(userRequest: UserRequest)
}

class AuthenticationViewModelImpl(
    private val navigator: Navigator,
    override val userUseCase: UserUseCase,
    override val preferencesStore: PreferencesStore
): KoinComponent, StatefulViewModelImpl<AuthenticationState>(), AuthenticationViewModel {

    private val mutableState = MutableStateFlow(
        AuthenticationState()
    )

    override val state: StateFlow<AuthenticationState>
        get() = mutableState

    override fun onLoginClick(userRequest: UserRequest) {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                val data = userUseCase.authenticationUserUser(userRequest)
                println(data.userResponse)
                mutableState.update {
                    it.copy(
                        statusResponse = data.statusResponse
                    )
                }
                when (data.statusResponse) {
                    StatusResponse.OK -> {
                        mutableState.update {
                            it.copy(
                                isError = false
                            )
                        }
                        data.userResponse?.user?.api_key?.let { preferencesStore.saveToken(it) }
                        navigator.navigateToProjects()
                    }
                    StatusResponse.Unauthorized -> {
                        mutableState.update {
                            it.copy(
                                isError = true,
                                messageError = "Неправильный логин или пароль"
                            )
                        }
                    }
                    else -> {
                        mutableState.update {
                            it.copy(
                                isError = true,
                                messageError = "Ошибка на стороне сервера"
                            )
                        }
                    }
                }
            })
        })

    }
}