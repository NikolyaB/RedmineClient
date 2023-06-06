package com.example.redmineclient.data.gateway.task

import com.example.redmineclient.data.storage.network.task.TaskServiceImpl
import com.example.redmineclient.domain.gateway.task.TaskGateway
import com.example.redmineclient.domain.models.tasks.Tasks
import com.example.redmineclient.domain.models.tasks.TasksResponse
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.*
import io.ktor.http.*

class TaskGatewayImpl(private val taskServiceImpl: TaskServiceImpl): TaskGateway {
    override suspend fun getTasks(apiKey: String, project_id: Int): TasksResponse {
        val response = taskServiceImpl.getTasks(apiKey = apiKey, project_id = project_id)
        return when (response.status) {
            HttpStatusCode.OK -> {
                println("response Tasks ${response.status}")
                println("RESPONSE Tasks ${response.body<Tasks>()}")
                TasksResponse(tasks = response.body<Tasks>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                println("response ${response.status}")
                TasksResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                println("response ${response.status}")
                TasksResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                TasksResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}