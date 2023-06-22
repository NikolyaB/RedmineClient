package com.example.redmineclient.domain.usecase.user

import com.example.redmineclient.domain.gateway.user.UserGateway
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.domain.models.UserResponse

class UserUseCase (private val userGateway: UserGateway) {
    suspend fun getCurrentUser(api_key: String): UserResponse = userGateway.getCurrentUser(api_key)
    suspend fun authenticationUserUser(userRequest: UserRequest): UserResponse = userGateway.authenticationUserUser(userRequest)
}