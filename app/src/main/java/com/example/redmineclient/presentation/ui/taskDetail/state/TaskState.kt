package com.example.redmineclient.presentation.ui.taskDetail.state

import com.example.redmineclient.domain.models.tasks.TaskAttribute
import com.example.redmineclient.domain.models.tasks.TaskInfo
import com.example.redmineclient.domain.state.LoadingState
import java.math.BigDecimal

data class TaskState(
    val loadingState: LoadingState = LoadingState.Loading,
    val taskInfo: TaskInfo? = null,
    val isEditTask: Boolean = false,
    val taskAttributes: List<TaskAttribute> = listOf(),
    val total_spent_hours: BigDecimal = "0.00".toBigDecimal(),
    val subject: String = "",
    val description: String = "",
    val tracker_id: Int = 1,
    val priority_id: Int = 1,
    val status_id: Int = 1,
    val done_ratio: Int = 0
)
