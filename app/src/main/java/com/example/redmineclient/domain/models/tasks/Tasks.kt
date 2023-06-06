package com.example.redmineclient.domain.models.tasks

import com.example.redmineclient.domain.models.ProjectInfo
import com.example.redmineclient.domain.state.StatusResponse
import kotlinx.serialization.Serializable

@Serializable
data class TasksResponse (
    val tasks: Tasks? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class Tasks(
    val issues: List<TaskInfo>
)

@Serializable
data class TaskInfo(
    val id: Int,
    val project: ProjectInfo,
    val tracker: TrackerTask,
    val status: StatusTask,
    val priority: PriorityTask,
    val subject: String,
    val description: String,
    val start_date: String,
    val done_ratio: Int,
    val total_spent_hours: Double,
)

@Serializable
data class TrackerTask(
    val id: Int,
    val name: String
)

@Serializable
data class StatusTask (
    val id: Int,
    val name: String,
    val is_closed: Boolean
)

@Serializable
data class PriorityTask (
    val id: Int,
    val name: String
)