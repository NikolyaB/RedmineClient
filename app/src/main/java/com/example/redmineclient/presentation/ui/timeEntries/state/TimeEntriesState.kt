package com.example.redmineclient.presentation.ui.timeEntries.state

import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.domain.state.LoadingState
import java.time.LocalDate

data class TimeEntriesState(
    val loadingState: LoadingState = LoadingState.Success,
    val date: LocalDate = LocalDate.now(),
    val timeEntryInfo: TimeEntriesInfo? = null,
    val numberTask: String = "0",
    val spentOn: String = "",
    val hoursTotal: Double = 0.0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val activity_id: Int = 10,
    val comments: String = ""
)
