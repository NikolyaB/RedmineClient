package com.example.redmineclient.user_management

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineclient.domain.user.SearchUsers
import com.example.redmineclient.domain.user.User
import com.example.redmineclient.domain.user.UserDataSource
import com.example.redmineclient.user_management.UserManagementState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserManagementViewModel(
    private val userDataSource: UserDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchUsers = SearchUsers()

    private val users = savedStateHandle.getStateFlow("users", emptyList<User>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(
        users,
        searchText,
        isSearchActive
    ) { users, searchText, isSearchActive ->
        UserManagementState(
            users = searchUsers.execute(users, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserManagementState())

    fun loadUsers() = viewModelScope.launch { savedStateHandle["users"] = userDataSource.getAllUsers() }

    fun onSearchTextChange(text: String) { savedStateHandle["searchText"] = text }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if(!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun changeGradeUser(isTeamLead: Boolean, id: Long){
        viewModelScope.launch {
            userDataSource.changeGradeUser(isTeamLead, id)
            loadUsers()
        }
    }

    fun deleteUserById(id: Long) {
        viewModelScope.launch {
            userDataSource.deleteUserById(id)
            loadUsers()
        }
    }
}