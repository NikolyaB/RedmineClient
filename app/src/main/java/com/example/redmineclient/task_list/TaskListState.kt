package com.example.redmineclient.task_list

import com.example.redmineclient.domain.task.Task

data class TaskListState (
    val tasks: List<Task> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false,
    val isTaskDone: Boolean = false,
    val selectedItemMenu: String = "",
    val isTeamLead: Boolean = false
)