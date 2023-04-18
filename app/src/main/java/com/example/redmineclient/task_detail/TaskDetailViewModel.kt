package com.example.redmineclient.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineclient.domain.task.Task
import com.example.redmineclient.domain.task.TaskDataSource
import com.example.redmineclient.domain.time.DateTimeUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskDataSource: TaskDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val taskTitle = savedStateHandle.getStateFlow("taskTitle", "")
    private val isTaskTitleFocused = savedStateHandle.getStateFlow("isTaskTitleFocused", false)
    private val taskContent = savedStateHandle.getStateFlow("taskContent", "")
    private val isTaskContentFocused = savedStateHandle.getStateFlow("isTaskContentFocused", false)
    private val taskPriority = savedStateHandle.getStateFlow("taskPriority", Task.generateRandomColor())
    private val taskColor = savedStateHandle.getStateFlow("taskColor", Task.generateRandomColor())


    val state = combine(
        taskTitle,
        isTaskTitleFocused,
        taskContent,
        isTaskContentFocused,
        taskPriority,
        taskColor
    ) { task ->
        TaskDetailState(
            taskTitle = task[0] as String,
            isTaskTitleHintVisible = (task[0] as String).isEmpty() && !(task[1] as Boolean),
            taskContent = task[2] as String,
            isTaskContentHintVisible = (task[2] as String).isEmpty() && !(task[3] as Boolean),
            taskPriority = task[4] as Long,
            taskColor = task[5] as Long
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskDetailState())

    private val _hasTaskBeenSaved = MutableStateFlow(false)
    val hasTaskBeenSaved = _hasTaskBeenSaved.asStateFlow()

    private val _userGrade = MutableStateFlow(0)
    val userGrade = _userGrade.asStateFlow()

    private var existingTaskId: Long? = null
    private var existingUserId: Long? = null

    init {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if(existingUserId == -1L) return@let
            this.existingUserId = existingUserId
        }
        savedStateHandle.get<Long>("taskId")?.let { existingTaskId ->
            if(existingTaskId == -1L) return@let
            this.existingTaskId = existingTaskId
            viewModelScope.launch {
                taskDataSource.getTaskById(existingTaskId)?.let { task ->
                    savedStateHandle["taskTitle"] = task.title
                    savedStateHandle["taskContent"] = task.content
                    savedStateHandle["taskPriority"] = task.priority
                    savedStateHandle["taskColor"] = task.colorHex
                }
            }
        }
    }

    fun onTaskTitleChanged(text: String) { savedStateHandle["taskTitle"] = text }

    fun onTaskContentChanged(text: String) { savedStateHandle["taskContent"] = text }

    fun onTaskTitleFocusChanged(isFocused: Boolean) { savedStateHandle["isTaskTitleFocused"] = isFocused }

    fun onTaskContentFocusChanged(isFocused: Boolean) { savedStateHandle["isTaskContentFocused"] = isFocused }

    fun onTaskPriorityChanged(value: Long) { savedStateHandle["taskPriority"] = value }


    fun saveTask() {
        viewModelScope.launch {
            taskDataSource.insertTask(
                Task(
                    id = existingTaskId,
                    title = taskTitle.value,
                    content =  taskContent.value,
                    priority = taskPriority.value,
                    isDone = false,
                    colorHex = taskColor.value,
                    created = DateTimeUtil.now(),
                    userId = existingUserId
                )
            )
            _hasTaskBeenSaved.value = true
        }
    }
}