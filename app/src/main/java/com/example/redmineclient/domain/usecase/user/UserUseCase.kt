package com.example.redmineclient.domain.usecase.user

import com.example.redmineclient.domain.gateway.user.UserGateway
import com.example.redmineclient.domain.models.UserResponse
import com.example.redmineclient.domain.models.UserRequest

class UserUseCase (private val userGateway: UserGateway) {
    suspend fun getCurrentUser(apiKey: String): UserResponse = userGateway.getCurrentUser(apiKey)
    suspend fun authenticationUserUser(userRequest: UserRequest): UserResponse = userGateway.authenticationUserUser(userRequest)
}