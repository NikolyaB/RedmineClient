package com.example.redmineclient.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineclient.domain.task.SearchTasks
import com.example.redmineclient.domain.task.Task
import com.example.redmineclient.domain.task.TaskDataSource
import com.example.redmineclient.domain.user.UserDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val userDataSource: UserDataSource,
    private val taskDataSource: TaskDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchTasks = SearchTasks()

    private val tasks = savedStateHandle.getStateFlow("tasks", emptyList<Task>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)
    private val isDone = savedStateHandle.getStateFlow("isDone", false)
    private val selectedItemMenu = savedStateHandle.getStateFlow("selectedItemMenu", "Все задачи")
    private val isTeamLead = savedStateHandle.getStateFlow("isTeamLead", false)

    val state = combine(
        tasks,
        searchText,
        isSearchActive,
        selectedItemMenu,
        isTeamLead
    ) { tasks, searchText, isSearchActive, selectedItemMenu, isTeamLead ->
        TaskListState (
            tasks = searchTasks.execute(tasks, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive,
            selectedItemMenu = selectedItemMenu,
            isTeamLead = isTeamLead
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskListState())

    var existingUserId: Long? = null

    init {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if(existingUserId == -1L) return@let
            this.existingUserId = existingUserId
            viewModelScope.launch {
                userDataSource.getCurrentUser(existingUserId)?.let { user ->
                    savedStateHandle["isTeamLead"] = user.isTeamLead
                }
            }
        }
    }

    fun loadTasksCurrentUser() {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if(existingUserId == -1L) return@let
            this.existingUserId = existingUserId
            viewModelScope.launch {
                savedStateHandle["tasks"] = taskDataSource.getAllTasksByUserId(existingUserId)
            }
        }
    }

    private fun loadIsDoneTasks() {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if (existingUserId == -1L) return@let
            this.existingUserId = existingUserId
            viewModelScope.launch {
                savedStateHandle["tasks"] = taskDataSource.getAllTasksByIsDone(existingUserId)
            }
        }
    }

    private fun loadNotIsDoneTasks() {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if (existingUserId == -1L) return@let
            this.existingUserId = existingUserId
            viewModelScope.launch {
                savedStateHandle["tasks"] = taskDataSource.getAllTasksByNotIsDone(existingUserId)
            }
        }
    }

    private fun loadPriorityTask() {
        savedStateHandle.get<Long>("userId")?.let { existingUserId ->
            if (existingUserId == -1L) return@let
            this.existingUserId = existingUserId
            viewModelScope.launch {
                savedStateHandle["tasks"] = taskDataSource.getAllTasksByPriority(existingUserId)
            }
        }
    }

    fun loadFilteredTasks(item: String) {
        when (item) {
            "Все задачи" -> loadTasksCurrentUser()
            "По приоритету" -> loadPriorityTask()
            "Открытые" -> loadNotIsDoneTasks()
            "Закрытые" -> loadIsDoneTasks()
        }
    }

    fun onFilteredTextChange(text: String) { savedStateHandle["selectedItemMenu"] = text }

    fun onSearchTextChange(text: String) { savedStateHandle["searchText"] = text }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if(!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun updateStatusTask(value: Boolean, id: Long) {
        savedStateHandle["isDone"] = value
        viewModelScope.launch {
            taskDataSource.updateStatusTask(isDone.value, id)
            loadFilteredTasks(selectedItemMenu.value)
        }
    }

    fun deleteTaskById(id: Long) {
        viewModelScope.launch {
            taskDataSource.deleteTaskById(id)
            loadFilteredTasks(selectedItemMenu.value)
        }
    }
}