package com.example.redmineclient.presentation.ui.projects.state

import com.example.redmineclient.domain.models.ProjectInfo
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.state.LoadingState

data class ProjectsState(
    val loadingState: LoadingState = LoadingState.Loading,
    val statusResponse: StatusResponse? = null,
    val projectResponse: List<ProjectInfo> = listOf()
)
