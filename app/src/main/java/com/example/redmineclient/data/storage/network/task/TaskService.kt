package com.example.redmineclient.data.storage.network.task

import com.example.redmineclient.domain.models.tasks.TaskRequest
import io.ktor.client.statement.HttpResponse

interface TaskService {
    suspend fun getTask(api_key: String, project_id: Int, task_id: Int): HttpResponse?
    suspend fun getTasks(api_key: String, project_id: Int): HttpResponse?
    suspend fun putTask(api_key: String, taskRequest: TaskRequest): HttpResponse?
    suspend fun postTask(api_key: String, taskRequest: TaskRequest): HttpResponse?
}