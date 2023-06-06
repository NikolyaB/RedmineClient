package com.example.redmineclient.data.storage.network.task

import io.ktor.client.statement.*

interface TaskService {
    suspend fun getTasks(apiToken: String, project_id: Int): HttpResponse?
}