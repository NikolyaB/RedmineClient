package com.example.redmineclient.domain.gateway.task

import com.example.redmineclient.domain.models.ProjectResponse
import com.example.redmineclient.domain.models.tasks.TasksResponse

interface TaskGateway {
    suspend fun getTasks(apiKey: String, project_id: Int): TasksResponse
}