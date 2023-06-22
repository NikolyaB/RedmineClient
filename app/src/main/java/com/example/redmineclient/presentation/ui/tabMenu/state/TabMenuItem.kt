package com.example.redmineclient.presentation.ui.tabMenu.state

import com.example.redmineclient.domain.models.tasks.TaskInfo
import com.example.redmineclient.domain.models.tasks.TaskWithTimer
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.domain.state.LoadingState
import java.util.Timer
import kotlin.time.Duration

enum class TabMenuItem {
    Tasks,
    TimeEntries
}

data class TabMenuState(
    val loadingStateTasks: LoadingState = LoadingState.Loading,
    val loadingStateTimeEntries: LoadingState = LoadingState.Loading,
    val tabState:TabMenuItem = TabMenuItem.Tasks,
    val tasks: List<TaskInfo>? = null,
    val timeEntries: List<TimeEntriesInfo>? = null,
    val tasksTime: List<TaskWithTimer> = listOf(),
    val timer: Timer = Timer(),
    val time: Duration = Duration.ZERO,
    val isTimerRunning: Boolean = false,
    val elapsedTime: Duration = Duration.ZERO,
    val seconds: String = "00",
    val minutes: String = "00",
    val hours: String = "00",
    val isPlaying: Boolean = false
)