package com.example.redmineclient.domain.usecase.project

import com.example.redmineclient.domain.gateway.project.ProjectGateway
import com.example.redmineclient.domain.models.ProjectResponse

class ProjectUseCase(private val projectGateway: ProjectGateway) {
    suspend fun getProjects(apiKey: String): ProjectResponse = projectGateway.getProjects(apiKey)
}