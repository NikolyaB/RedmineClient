package com.example.redmineclient.data.storage.network.userR

import com.example.redmineclient.data.storage.network.HttpRoutes
import com.example.redmineclient.domain.models.UserRequest
import com.example.redmineclient.domain.models.UserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.lang.Exception

class UserServiceImpl(private val client: HttpClient): UserService {
    override suspend fun getCurrentUser(apiKey: String): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_CURRENT_USER)
            parameter("key", apiKey)
        }
    }

    override suspend fun authenticationUser(user: UserRequest): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_CURRENT_USER)
            basicAuth(username = user.login, password = user.password)
        }
    }
}