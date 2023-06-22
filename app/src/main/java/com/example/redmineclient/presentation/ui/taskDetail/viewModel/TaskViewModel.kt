package com.example.redmineclient.presentation.ui.taskDetail.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.models.tasks.TaskAttribute
import com.example.redmineclient.domain.models.tasks.TaskParam
import com.example.redmineclient.domain.models.tasks.TaskRequest
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.usecase.task.TaskUseCase
import com.example.redmineclient.domain.usecase.user.UserUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.taskDetail.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface TaskViewModel: StatefulViewModel<TaskState> {
    override val state: StateFlow<TaskState>
    val taskUseCase: TaskUseCase
    val userUseCase: UserUseCase
    val preferencesStore: PreferencesStore
    fun getTask()
    fun onRefreshClick()
    fun onEditClick()
    fun onSaveClick()
    fun onCancelClick()
    fun onCancelEditClick()
    fun onCancelCreateClick()
    fun onAddTimeEntries()
    fun onSubjectChange(subject: String)
    fun onDescriptionChange(description: String)
    fun onTrackerClick(id: Int)
    fun onPriorityClick(id: Int)
    fun onStatusClick(id: Int)
    fun onDoneRatioClick(value: Int)
    fun onDoneRatioUpdate(value: Int)
    fun saveEdit()
    fun saveCreate()
}

class TaskViewModelImpl(
    private val navigator: Navigator,
    override val preferencesStore: PreferencesStore,
    override val taskUseCase: TaskUseCase,
    override val userUseCase: UserUseCase,
    private val project_id: Int,
    private val task_id: Int?,
): KoinComponent, StatefulViewModelImpl<TaskState>(), TaskViewModel {
    private val mutableState = MutableStateFlow(
        TaskState()
    )

    override val state: StateFlow<TaskState>
        get() = mutableState

    override fun onViewShown() {
        super.onViewShown()
        if (task_id != null) {
            getTask()
        } else {
            mutableState.update {
                it.copy(
                    loadingState = LoadingState.Success
                )
            }
        }
    }

    override fun getTask() {
        jobs.add (scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { api_key ->
                    val response = task_id?.let {
                        taskUseCase.getTask(
                            api_key = api_key,
                            project_id = project_id,
                            task_id = it
                        )
                    }
                    val statusResponse = response?.statusResponse
                    val data = response?.task
                    when (statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success,
                                    taskInfo = data!!,
                                    taskAttributes = listOf(
                                        TaskAttribute(title = "Трекер", name = data.tracker.name),
                                        TaskAttribute(title = "Статус", name = data.status.name),
                                        TaskAttribute(title = "Приоритет", name = data.priority.name),
                                        TaskAttribute(title = "Трудозатраы", name = data.total_spent_hours.toString())
                                    ),
                                    subject = data.subject,
                                    tracker_id = data.tracker.id,
                                    priority_id = data.priority.id,
                                    status_id = data.status.id,
                                    done_ratio = data.done_ratio,
                                    description = data.description,
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

    override fun onRefreshClick() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        loadingState = LoadingState.Loading
                    )
                }
            })
        })
        getTask()
    }

    override fun onEditClick() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        isEditTask = true
                    )
                }
            })
        })
    }

    override fun onSaveClick() {
        if (task_id == null) {
            saveCreate()
        } else {
            saveEdit()
        }
    }

    override fun onCancelClick() {
        if (task_id == null) {
            onCancelCreateClick()
        } else {
            onCancelEditClick()
        }
    }

    override fun onCancelEditClick() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        isEditTask = false
                    )
                }
            })
        })
    }

    override fun onCancelCreateClick() {
        navigator.navigateBack()
    }

    override fun onAddTimeEntries() {
        navigator.navigateToTimeEntriesEdit()
    }

    override fun onSubjectChange(subject: String) {
        mutableState.update {
            it.copy(
                subject = subject
            )
        }
    }

    override fun onDescriptionChange(description: String) {
        mutableState.update {
            it.copy(
                description = description
            )
        }
    }

    override fun onTrackerClick(id: Int) {
        mutableState.update {
            it.copy(
                tracker_id = id
            )
        }
    }

    override fun onPriorityClick(id: Int) {
        mutableState.update {
            it.copy(
                priority_id = id
            )
        }
    }

    override fun onStatusClick(id: Int) {
        mutableState.update {
            it.copy(
                status_id = id
            )
        }
    }

    override fun onDoneRatioClick(value: Int) {
        mutableState.update {
            it.copy(
                done_ratio = value
            )
        }
    }

    override fun onDoneRatioUpdate(value: Int) {
        mutableState.update {
            it.copy(
                done_ratio = value
            )
        }
    }

    override fun saveEdit() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { api_key ->
                    mutableState.update {
                        it.copy(
                            loadingState = LoadingState.Loading
                        )
                    }
                    val response = taskUseCase.putTask(
                        api_key = api_key,
                        taskRequest = TaskRequest(
                            TaskParam(
                                issue_id = task_id,
                                project_id = project_id,
                                subject = state.value.subject,
                                tracker_id = state.value.tracker_id,
                                priority_id = state.value.priority_id,
                                status_id = state.value.status_id,
                                done_ratio = state.value.done_ratio,
                                description = state.value.description
                            )
                        )
                    )
                    when (response.statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success,
                                    isEditTask = false
                                )
                            }
                            getTask()
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

    override fun saveCreate() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { api_key ->
                    mutableState.update {
                        it.copy(
                            loadingState = LoadingState.Loading
                        )
                    }
                    val responseUser = userUseCase.getCurrentUser(api_key = api_key)
                    when (responseUser.statusResponse) {
                        StatusResponse.OK -> {
                            val response = taskUseCase.postTask(
                                api_key = api_key,
                                taskRequest = TaskRequest(
                                    TaskParam(
                                        assigned_to_id = responseUser.userResponse?.user?.id,
                                        project_id = project_id,
                                        subject = state.value.subject,
                                        tracker_id = state.value.tracker_id,
                                        priority_id = state.value.priority_id,
                                        status_id = state.value.status_id,
                                        done_ratio = state.value.done_ratio,
                                        description = state.value.description
                                    )
                                )
                            )
                            when (response.statusResponse) {
                                StatusResponse.Created -> {
                                    mutableState.update {
                                        it.copy(
                                            loadingState = LoadingState.Success
                                        )
                                    }
                                    navigator.navigateBack()
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
}