package com.example.redmineclient.data.storage.network.user

import com.example.redmineclient.domain.models.UserRequest
import io.ktor.client.statement.HttpResponse

interface UserService {
    suspend fun getCurrentUser(apiKey: String): HttpResponse
    suspend fun authenticationUser(user: UserRequest): HttpResponse
}