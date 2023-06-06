package com.example.redmineclient.presentation.ui.tabMenu.state

import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.domain.models.tasks.TaskInfo
import com.example.redmineclient.domain.state.LoadingState

enum class TabMenuItem {
    Tasks,
    TimeEntries
}

data class TabMenuState (
    val loadingStateTasks: LoadingState = LoadingState.Loading,
    val loadingStateTimeEntries: LoadingState = LoadingState.Loading,
    val tabState:TabMenuItem = TabMenuItem.Tasks,
    val tasks: List<TaskInfo>? = null,
    val timeEntries: List<TimeEntriesInfo>? = null
)