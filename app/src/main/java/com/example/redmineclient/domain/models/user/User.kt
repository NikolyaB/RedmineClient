package com.example.redmineclient.domain.models

import com.example.redmineclient.domain.state.StatusResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userResponse: User? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class User(
    val user: UserInfo
)

@Serializable
data class UserInfo(
    val id: Int,
    val login: String,
    val firstname: String,
    val lastname: String,
    val mail: String,
    val api_key: String
)

@Serializable
data class  UserRequest(
    val login: String,
    val password: String
)