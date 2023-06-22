package com.example.redmineclient.data.storage.network.project

import com.example.redmineclient.data.storage.network.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ProjectServiceImpl(private val client: HttpClient): ProjectService {
    override suspend fun getProjects(apiKey: String): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_PROJECTS)
            parameter(key = "key", apiKey)
        }
    }
}