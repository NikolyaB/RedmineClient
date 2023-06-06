package com.example.redmineclient.data.storage.network.task

import com.example.redmineclient.data.storage.network.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class TaskServiceImpl(private val client: HttpClient): TaskService {
    override suspend fun getTasks(apiKey: String, project_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TASKS)
            parameter(key = "key", apiKey)
            parameter(key = "project_id", project_id)
            parameter(key = "assigned_to_id", "me")
        }
    }
}