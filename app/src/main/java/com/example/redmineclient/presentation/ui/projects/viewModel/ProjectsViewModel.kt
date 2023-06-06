package com.example.redmineclient.presentation.ui.projects.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.models.ProjectInfo
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.domain.usecase.project.ProjectUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.projects.state.ProjectsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface ProjectsViewModel: StatefulViewModel<ProjectsState> {
    override val state: StateFlow<ProjectsState>
    val projectsUseCase: ProjectUseCase
    val preferencesStore: PreferencesStore
    fun getProjects()
    fun onProjectClick(projectInfo: ProjectInfo)
    fun onProfileClick()
    fun onRefreshClick()
}

class ProjectsViewModelImpl(
    private val navigator: Navigator,
    override val preferencesStore: PreferencesStore,
    override val projectsUseCase: ProjectUseCase
): KoinComponent, StatefulViewModelImpl<ProjectsState>(), ProjectsViewModel {

    private val mutableState = MutableStateFlow(
        ProjectsState()
    )

    override val state: StateFlow<ProjectsState>
        get() = mutableState

    override fun onViewShown() {
        super.onViewShown()
        getProjects()
    }

    override fun getProjects() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { apiKey ->
                    val data = projectsUseCase.getProjects(apiKey)
                    println(data.projectsResponse)
                    mutableState.update {
                        it.copy(
                            statusResponse = data.statusResponse
                        )
                    }
                    when (state.value.statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success,
                                    projectResponse = data.projectsResponse!!.projects
                                )
                            }
                        }
                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun onProjectClick(projectInfo: ProjectInfo) {
        val id = projectInfo.id
        navigator.navigateToTabMenu(project_id = id)
    }

    override fun onProfileClick() {
        navigator.navigateToProfile()
    }

    override fun onRefreshClick() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        loadingState = LoadingState.Loading,
                    )
                }
                getProjects()
            })
        })
    }
}