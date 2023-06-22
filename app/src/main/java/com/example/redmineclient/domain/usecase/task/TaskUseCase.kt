package com.example.redmineclient.domain.usecase.task

import com.example.redmineclient.domain.gateway.task.TaskGateway
import com.example.redmineclient.domain.models.tasks.TaskRequest
import com.example.redmineclient.domain.models.tasks.TaskResponse
import com.example.redmineclient.domain.models.tasks.TasksResponse

class TaskUseCase(private val taskGateway: TaskGateway) {
    suspend fun getTask(api_key: String, project_id: Int, task_id: Int): TaskResponse =
        taskGateway.getTask(
            api_key = api_key,
            project_id = project_id,
            task_id = task_id
        )
    suspend fun getTasks(api_key: String, project_id: Int):
            TasksResponse = taskGateway.getTasks(api_key = api_key, project_id = project_id)
    suspend fun putTask(api_key: String, taskRequest: TaskRequest): TaskResponse =
        taskGateway.putTask(api_key = api_key, taskRequest = taskRequest)
    suspend fun postTask(api_key: String, taskRequest: TaskRequest): TaskResponse =
        taskGateway.postTask(api_key = api_key, taskRequest = taskRequest)
}