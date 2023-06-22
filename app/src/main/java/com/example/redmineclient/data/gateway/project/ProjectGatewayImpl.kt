package com.example.redmineclient.data.gateway.project

import com.example.redmineclient.data.storage.network.project.ProjectServiceImpl
import com.example.redmineclient.domain.gateway.project.ProjectGateway
import com.example.redmineclient.domain.models.ProjectResponse
import com.example.redmineclient.domain.models.Projects
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class ProjectGatewayImpl (private val projectServiceImpl: ProjectServiceImpl): ProjectGateway {
    override suspend fun getProjects(api_key: String): ProjectResponse {
        val response = projectServiceImpl.getProjects(api_key)
        return when (response.status) {
            HttpStatusCode.OK -> {
                println("response ${response.status}")
                ProjectResponse(projectsResponse = response.body<Projects>(), statusResponse = StatusResponse.OK)
            }
            HttpStatusCode.Unauthorized -> {
                println("response ${response.status}")
                ProjectResponse(statusResponse = StatusResponse.Unauthorized)
            }
            HttpStatusCode.BadGateway -> {
                println("response ${response.status}")
                ProjectResponse(projectsResponse = response.body(), statusResponse = StatusResponse.BadGateway)
            }
            else -> {
                ProjectResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

}