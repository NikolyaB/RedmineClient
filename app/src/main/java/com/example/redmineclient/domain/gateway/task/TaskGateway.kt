package com.example.redmineclient.domain.gateway.task

import com.example.redmineclient.domain.models.tasks.TaskRequest
import com.example.redmineclient.domain.models.tasks.TaskResponse
import com.example.redmineclient.domain.models.tasks.TasksResponse

interface TaskGateway {
    suspend fun getTask(api_key: String, project_id: Int, task_id: Int): TaskResponse
    suspend fun getTasks(api_key: String, project_id: Int): TasksResponse
    suspend fun putTask(api_key: String, taskRequest: TaskRequest): TaskResponse
    suspend fun postTask(api_key: String, taskRequest: TaskRequest): TaskResponse
}