package com.example.redmineclient.data.storage.network.task

import com.example.redmineclient.data.storage.network.HttpRoutes
import com.example.redmineclient.domain.models.tasks.TaskRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TaskServiceImpl(private val client: HttpClient): TaskService {
    override suspend fun getTask(api_key: String, project_id: Int, task_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TASK)
            parameter(key = "key", api_key)
            parameter(key = "project_id", project_id)
            parameter(key = "issue_id", task_id)
        }
    }

    override suspend fun getTasks(api_key: String, project_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TASKS)
            parameter(key = "key", api_key)
            parameter(key = "project_id", project_id)
            parameter(key = "assigned_to_id", "me")
        }
    }

    override suspend fun putTask(api_key: String, taskRequest: TaskRequest): HttpResponse {
        return client.put {
            url(HttpRoutes.PUT_TASK + taskRequest.issue.issue_id + HttpRoutes.TYPE)
            parameter(key = "key", api_key)
            contentType(ContentType.Application.Json)
            setBody(taskRequest)
        }
    }

    override suspend fun postTask(api_key: String, taskRequest: TaskRequest): HttpResponse {
        return client.post {
            url(HttpRoutes.POST_TASK)
            parameter(key = "key", api_key)
            contentType(ContentType.Application.Json)
            setBody(taskRequest)
        }
    }

}