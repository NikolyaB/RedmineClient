package com.example.redmineclient.data.user

import com.example.redmineclient.domain.user.User
import database.UserEntity

fun UserEntity.toUser(): User {
    return User (
        id = id,
        login = login,
        password = password,
        isTeamLead = isTeamLead
    )
}