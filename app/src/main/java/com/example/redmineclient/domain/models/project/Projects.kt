package com.example.redmineclient.domain.models

import com.example.redmineclient.domain.state.StatusResponse
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse (
    val projectsResponse: Projects? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class Projects (
    val projects: List<ProjectInfo>
)

@Serializable
data class ProjectInfo (
    val id: Int,
    val name: String,
)