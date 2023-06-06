package com.example.redmineclient.data.storage.network.projectR

import io.ktor.client.statement.*


interface ProjectService {
    suspend fun getProjects(apiKey: String): HttpResponse?
}