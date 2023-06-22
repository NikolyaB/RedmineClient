package com.example.redmineclient.data.gateway.user

import com.example.redmineclient.data.storage.network.user.UserServiceImpl
import com.example.redmineclient.domain.gateway.user.UserGateway
import com.example.redmineclient.domain.models.User
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.domain.models.UserResponse
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class UserGatewayImpl (private val userServiceImpl: UserServiceImpl): UserGateway {
    override suspend fun getCurrentUser(api_key: String): UserResponse {
        val response = userServiceImpl.getCurrentUser(api_key)
        return when (response.status) {
            HttpStatusCode.OK -> {
                UserResponse(userResponse = response.body<User>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                UserResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                UserResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                UserResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
    override suspend fun authenticationUserUser(userRequest: UserRequest): UserResponse {
        val response = userServiceImpl.authenticationUser(userRequest)
        return when (response.status) {
            HttpStatusCode.OK -> {
                UserResponse(userResponse = response.body<User>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                UserResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                UserResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                UserResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}