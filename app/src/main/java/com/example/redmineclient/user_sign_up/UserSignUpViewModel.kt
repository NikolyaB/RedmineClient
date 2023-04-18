package com.example.redmineclient.user_sign_up

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineclient.domain.user.User
import com.example.redmineclient.domain.user.UserDataSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserSignUpViewModel(
    private val userDataSource: UserDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val login = savedStateHandle.getStateFlow("login", "")
    private val isLoginFocused = savedStateHandle.getStateFlow("isLoginFocused", false)
    private val passwordFirst = savedStateHandle.getStateFlow("passwordFirst", "")
    private val isPasswordFirstFocused = savedStateHandle.getStateFlow("isPasswordFirstFocused", false)
    private val passwordLast = savedStateHandle.getStateFlow("passwordLast", "")
    private val isPasswordLastFocused = savedStateHandle.getStateFlow("isPasswordLastFocused", false)
    private val isTeamLead = savedStateHandle.getStateFlow("isTeamLead", false)

    val state = combine(
        login,
        isLoginFocused,
        passwordFirst,
        isPasswordFirstFocused,
        passwordLast,
        isPasswordLastFocused,
        isTeamLead
    ) { user ->
        UserSignUpState(
            login = user[0] as String,
            isLoginHintVisible = (user[0] as String).isEmpty() && !(user[1] as Boolean),
            passwordFirst = user[2] as String,
            isPasswordFirstHintVisible = (user[2] as String).isEmpty() && !(user[3] as Boolean),
            passwordLast = user[4] as String,
            isPasswordLastHintVisible = (user[4] as String).isEmpty() && !(user[5] as Boolean),
            isTeamLead = user[6] as Boolean
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserSignUpState())

    private val _hasUserBeenSaved = MutableStateFlow(false)
    val hasUserBeenSaved = _hasUserBeenSaved.asStateFlow()

    private val existingUserId: Long? = null

    fun onLoginChanged(text: String) { savedStateHandle["login"] = text }

    fun onPasswordFirstChanged(text: String) { savedStateHandle["passwordFirst"] = text }

    fun onPasswordLastChanged(text: String) { savedStateHandle["passwordLast"] = text }

    fun onLoginFocusChanged(isFocused: Boolean) { savedStateHandle["isLoginFocused"] = isFocused }

    fun onPasswordFirstFocusChanged(isFocused: Boolean) { savedStateHandle["isPasswordFirstFocused"] = isFocused }

    fun onPasswordLastFocusChanged(isFocused: Boolean) { savedStateHandle["isPasswordLastFocused"] = isFocused }

    fun isTeamLead(value: Boolean) { savedStateHandle["isTeamLead"] = value }


    fun userSignUp() {
        viewModelScope.launch {
            userDataSource.insertUser(
                User(
                    id = existingUserId,
                    login = login.value,
                    password = passwordFirst.value,
                    isTeamLead = isTeamLead.value
                )
            )
            _hasUserBeenSaved.value = true
        }
    }

}