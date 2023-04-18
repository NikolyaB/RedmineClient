package com.example.redmineclient.domain.user

import com.example.redmineclient.domain.user.User

interface UserDataSource {
    suspend fun insertUser(user: User)
    suspend fun loginUser(login: String, password: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUserById(id: Long)
    suspend fun getCurrentUser(id: Long): User?
    suspend fun changeGradeUser(isTeamLead: Boolean, id: Long)
}