package com.example.redmineclient.data.storage.network.user

import com.example.redmineclient.data.storage.network.HttpRoutes
import com.example.redmineclient.domain.models.UserRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

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