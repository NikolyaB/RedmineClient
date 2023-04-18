package com.example.redmineclient.user_management

import com.example.redmineclient.domain.user.User

data class UserManagementState(
    val users: List<User> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false,
)