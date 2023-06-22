package com.example.redmineclient.data.storage.network.project

import io.ktor.client.statement.*


interface ProjectService {
    suspend fun getProjects(apiKey: String): HttpResponse?
}