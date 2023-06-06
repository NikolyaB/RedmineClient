package com.example.redmineclient.domain.gateway.project

import com.example.redmineclient.domain.models.ProjectResponse

interface ProjectGateway {
    suspend fun getProjects(apiKey: String): ProjectResponse
}