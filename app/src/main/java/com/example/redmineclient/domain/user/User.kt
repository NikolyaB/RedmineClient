package com.example.redmineclient.domain.user

data class User (
    val id: Long?,
    val login: String,
    val password: String,
    val isTeamLead: Boolean,
)