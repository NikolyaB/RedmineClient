package com.example.redmineclient.presentation.ui.tabMenu.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.domain.models.tasks.TaskInfo
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.usecase.task.TaskUseCase
import com.example.redmineclient.domain.usecase.timeEntries.TimeEntriesUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuItem
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface TabMenuViewModel: StatefulViewModel<TabMenuState> {
    override val state: StateFlow<TabMenuState>
    val preferencesStore: PreferencesStore
    val taskUseCase: TaskUseCase
    val timeEntriesUseCase: TimeEntriesUseCase
    fun onTabClick(tab: TabMenuItem): Boolean
    fun onTaskClick(task: TaskInfo)
    fun onTimeEntriesClick(timeEntries: TimeEntriesInfo)
    fun onTasksRefresh()
    fun onTimeEntriesRefresh()
    fun getTasks()
    fun getTimeEntries()
}

class TabMenuViewModelImpl(
    private val navigator: Navigator,
    override val preferencesStore: PreferencesStore,
    override val taskUseCase: TaskUseCase,
    override val timeEntriesUseCase: TimeEntriesUseCase,
    private val project_id: Int,
): KoinComponent, StatefulViewModelImpl<TabMenuState>(), TabMenuViewModel {

    private val mutableState = MutableStateFlow(
        TabMenuState()
    )
    override val state: StateFlow<TabMenuState>
        get() = mutableState

    override fun onTabClick(tab: TabMenuItem): Boolean {
        val result = tab != mutableState.value.tabState
        mutableState.update { it.copy(tabState = tab) }
        return result
    }

    override fun onTaskClick(task: TaskInfo) {
        navigator.navigateToTaskDetail()
    }

    override fun onTimeEntriesClick(timeEntries: TimeEntriesInfo) {
        navigator.navigateToTimeEntriesEdit()
    }

    override fun onViewShown() {
        super.onViewShown()
        getTasks()
        getTimeEntries()
    }

    override fun getTasks() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { key ->
                    val response = taskUseCase.getProjects(apiKey = key, project_id = project_id)
                    val data = response.tasks?.issues

                    when (response.statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingStateTasks = LoadingState.Success,
                                    tasks = data
                                )
                            }
                            println(data)
                        }
                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingStateTasks = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun getTimeEntries() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { key ->
                    val response = timeEntriesUseCase.getTimeEntries(apiKey = key, project_id = project_id)
                    val data = response.timeEntries?.time_entries

                    when (response.statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingStateTimeEntries = LoadingState.Success,
                                    timeEntries = data
                                )
                            }
                        }
                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingStateTimeEntries = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun onTasksRefresh() {
        TODO("Not yet implemented")
    }

    override fun onTimeEntriesRefresh() {
        TODO("Not yet implemented")
    }
}