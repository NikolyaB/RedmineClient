package com.example.redmineclient.data.gateway.task

import com.example.redmineclient.data.storage.network.task.TaskServiceImpl
import com.example.redmineclient.domain.gateway.task.TaskGateway
import com.example.redmineclient.domain.models.tasks.TaskRequest
import com.example.redmineclient.domain.models.tasks.TaskResponse
import com.example.redmineclient.domain.models.tasks.Tasks
import com.example.redmineclient.domain.models.tasks.TasksResponse
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class TaskGatewayImpl(private val taskServiceImpl: TaskServiceImpl): TaskGateway {
    override suspend fun getTask(api_key: String, project_id: Int, task_id: Int): TaskResponse {
        val response = taskServiceImpl.getTask(
            api_key = api_key,
            project_id = project_id,
            task_id = task_id
        )
        return when (response.status) {
            HttpStatusCode.OK -> {
                TaskResponse ( task =
                    response.body<Tasks>().issues.component1(),
                    statusResponse = StatusResponse.OK
                )
            }
            HttpStatusCode.Unauthorized -> {
                TaskResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                TaskResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                TaskResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun getTasks(api_key: String, project_id: Int): TasksResponse {
        val response = taskServiceImpl.getTasks(api_key = api_key, project_id = project_id)
        return when (response.status) {
            HttpStatusCode.OK -> {
                TasksResponse(tasks = response.body<Tasks>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                TasksResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                TasksResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                TasksResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun putTask(api_key: String, taskRequest: TaskRequest): TaskResponse {
        val response = taskServiceImpl.putTask(api_key = api_key, taskRequest = taskRequest)
        return when (response.status) {
            HttpStatusCode.NoContent -> {
                TaskResponse(statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.OK -> {
                TaskResponse(statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                TaskResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                TaskResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                TaskResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun postTask(api_key: String, taskRequest: TaskRequest): TaskResponse {
        val response = taskServiceImpl.postTask(api_key = api_key, taskRequest = taskRequest)
        println("ResponseVI  ${response.status}")
        return when (response.status) {
            HttpStatusCode.Created -> {
                TaskResponse(statusResponse = StatusResponse.Created)
            }
            HttpStatusCode.Unauthorized -> {
                TaskResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                TaskResponse(statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                TaskResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}