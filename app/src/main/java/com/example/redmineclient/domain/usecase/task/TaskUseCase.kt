package com.example.redmineclient.domain.usecase.task

import com.example.redmineclient.domain.gateway.project.ProjectGateway
import com.example.redmineclient.domain.gateway.task.TaskGateway
import com.example.redmineclient.domain.models.ProjectResponse
import com.example.redmineclient.domain.models.tasks.TasksResponse

class TaskUseCase(private val taskGateway: TaskGateway) {
    suspend fun getProjects(apiKey: String, project_id: Int):
            TasksResponse = taskGateway.getTasks(apiKey = apiKey, project_id = project_id)
}