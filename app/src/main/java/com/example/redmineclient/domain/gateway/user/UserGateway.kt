package com.example.redmineclient.domain.gateway.user

import com.example.redmineclient.domain.models.UserResponse
import com.example.redmineclient.domain.models.UserRequest

interface UserGateway {
    suspend fun getCurrentUser(apiKey: String): UserResponse
    suspend fun authenticationUserUser(userRequest: UserRequest): UserResponse
}