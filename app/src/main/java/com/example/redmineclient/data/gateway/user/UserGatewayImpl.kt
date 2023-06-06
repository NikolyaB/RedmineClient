package com.example.redmineclient.data.gateway.user

import com.example.redmineclient.data.storage.network.userR.UserServiceImpl
import com.example.redmineclient.domain.gateway.user.UserGateway
import com.example.redmineclient.domain.models.User
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.models.UserResponse
import com.example.redmineclient.domain.models.UserRequest
import io.ktor.client.call.*
import io.ktor.http.*

class UserGatewayImpl (private val userServiceImpl: UserServiceImpl): UserGateway {
    override suspend fun getCurrentUser(apiKey: String): UserResponse {
        val response = userServiceImpl.getCurrentUser(apiKey)
        return when (response.status) {
            HttpStatusCode.OK -> {
                println("response ${response.status}")
                UserResponse(userResponse = response.body<User>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                println("response ${response.status}")
                UserResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                println("response ${response.status}")
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
                println("response ${response.status}")
                UserResponse(userResponse = response.body<User>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                println("response ${response.status}")
                UserResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                println("response ${response.status}")
                UserResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                UserResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}