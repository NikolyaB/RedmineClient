package com.example.redmineclient.presentation.ui.tabMenu.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.models.tasks.TaskWithTimer
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
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
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

interface TabMenuViewModel: StatefulViewModel<TabMenuState> {
    override val state: StateFlow<TabMenuState>
    val preferencesStore: PreferencesStore
    val taskUseCase: TaskUseCase
    val timeEntriesUseCase: TimeEntriesUseCase
    fun onTabClick(tab: TabMenuItem): Boolean
    fun onTaskClick(task: TaskWithTimer)
    fun onTasksRefresh()
    fun onTimeEntriesRefresh()
    fun getTasks()
    fun getTimeEntries()
    fun onCreateTaskClick()
    fun onCreateTimeEntriesClick()
    fun onDeleteTimeEntriesClick(timeEntries: TimeEntriesInfo)
    fun onEditTimeEntriesClick(timeEntries: TimeEntriesInfo)
    fun onStartClick(task: TaskWithTimer)
    fun onPauseClick(task: TaskWithTimer)
    fun onStopClick(task: TaskWithTimer)
    fun formatTime(duration: Long): String
}
@ExperimentalTime
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

    override fun onTaskClick(task: TaskWithTimer) {
        navigator.navigateToTaskDetail(task_id = task.id, project_id = task.project.id)
    }

    override fun onEditTimeEntriesClick(timeEntries: TimeEntriesInfo) {
        navigator.navigateToTimeEntriesEdit(time_entries_id = timeEntries.id)
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
                    val response = taskUseCase.getTasks(api_key = key, project_id = project_id)
                    val statusResponse = response.statusResponse
                    val data = response.tasks?.issues

                    when (statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingStateTasks = LoadingState.Success,
                                    tasks = data!!,
                                    tasksTime = data.map {
                                        TaskWithTimer(
                                            id = it.id,
                                            project = it.project,
                                            tracker = it.tracker,
                                            status = it.status,
                                            priority = it.priority,
                                            assigned_to = it.assigned_to,
                                            subject = it.subject,
                                            description = it.description,
                                            start_date = it.start_date,
                                            done_ratio = it.done_ratio,
                                            total_spent_hours = it.total_spent_hours,
                                            elapsedTime = 0L
                                        )
                                    }
                                )
                            }
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
                    val response =
                        timeEntriesUseCase.getTimeEntries(api_key = key, project_id = project_id)
                    val statusResponse = response.statusResponse
                    val data = response.timeEntries?.time_entries

                    when (statusResponse) {
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

    override fun onCreateTaskClick() {
        navigator.navigateToTaskDetail(project_id = project_id)
    }

    override fun onCreateTimeEntriesClick() {
        navigator.navigateToTimeEntriesEdit()
    }

    override fun onDeleteTimeEntriesClick(timeEntries: TimeEntriesInfo) {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { key ->
                    val response =
                        timeEntriesUseCase.deleteTimeEntries(api_key = key, timeEntries.id)
                    val statusResponse = response.statusResponse
                    mutableState.update {
                        it.copy(
                            loadingStateTimeEntries = LoadingState.Loading
                        )
                    }
                    when (statusResponse) {
                        StatusResponse.NoContent -> {
                            getTimeEntries()
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

    var time: Duration = Duration.ZERO
    lateinit var timer: Timer

    override fun onStartClick(task: TaskWithTimer) {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.seconds)
            mutableState.update {
                it.copy(
                    timer = timer,
                    time = time,
                    isPlaying = true
                )
            }
            updateTimeStates()
        }
    }
    private fun updateTimeStates() {
        mutableState.update { state ->
            state.time.toComponents { hours, minutes, seconds, _ ->
                state.copy(
                    seconds = seconds.pad(),
                    minutes = minutes.pad(),
                    hours = hours.pad()
                )
            }
        }
    }


    override fun onPauseClick(task: TaskWithTimer) {
        timer.cancel()
        mutableState.update {
            it.copy(
                isPlaying = false
            )
        }
    }

    override fun onStopClick(task: TaskWithTimer) {
        onPauseClick(task)
        mutableState.update {
            it.copy(
                time = Duration.ZERO
            )
        }
        updateTimeStates()
    }

    override fun formatTime(duration: Long): String {
        val durationInMillis = duration * 1000L
        val formattedDuration = Duration.milliseconds(durationInMillis)
        val hours = formattedDuration.inHours
        val minutes = formattedDuration.inMinutes % 60
        val seconds = formattedDuration.inSeconds % 60
        return "%02d:%02d:%02d".format(hours.toInt(), minutes.toInt(), seconds.toInt())
    }
}

private fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}
private fun Long.pad(): String {
    return this.toString().padStart(2, '0')
}
