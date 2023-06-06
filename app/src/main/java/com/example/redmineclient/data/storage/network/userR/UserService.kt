package com.example.redmineclient.data.storage.network.userR

import com.example.redmineclient.domain.models.UserRequest
import io.ktor.client.statement.*

interface UserService {
    suspend fun getCurrentUser(apiKey: String): HttpResponse
    suspend fun authenticationUser(user: UserRequest): HttpResponse
}