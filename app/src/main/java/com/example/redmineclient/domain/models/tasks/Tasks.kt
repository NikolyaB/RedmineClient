package com.example.redmineclient.domain.models.tasks

import com.example.redmineclient.domain.models.ProjectInfo
import com.example.redmineclient.domain.state.StatusResponse
import kotlinx.serialization.Serializable
import java.util.Timer
import kotlin.time.Duration

@Serializable
data class TasksResponse (
    val tasks: Tasks? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class TaskResponse(
    val task: TaskInfo? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class TaskRequest(
    val issue: TaskParam
)

@Serializable
data class TaskParam(
    val issue_id: Int? = null,
    val assigned_to_id: Int? = null,
    val project_id: Int,
    val subject: String,
    val tracker_id: Int,
    val priority_id: Int,
    val status_id: Int,
    val done_ratio: Int,
    val description: String
)

@Serializable
data class Tasks(
    val issues: List<TaskInfo>
)

@Serializable
data class TaskInfo(
    val id: Int,
    val project: ProjectInfo,
    val tracker: TaskTracker,
    val status: TaskStatus,
    val priority: TaskPriority,
    val assigned_to: TaskAssignedTo,
    var subject: String,
    var description: String,
    val start_date: String,
    val done_ratio: Int,
    val total_spent_hours: Double,
)

data class TaskWithTimer(
    val id: Int,
    val project: ProjectInfo,
    val tracker: TaskTracker,
    val status: TaskStatus,
    val priority: TaskPriority,
    val assigned_to: TaskAssignedTo,
    var subject: String,
    var description: String,
    val start_date: String,
    val done_ratio: Int,
    val total_spent_hours: Double,
    val elapsedTime: Long,
    val time: Duration = Duration.ZERO,
    val timer: Timer = Timer(),
    val seconds: String = "00",
    val minutes: String = "00",
    val hours: String = "00",
    val isPlaying: Boolean = false
)

@Serializable
data class TaskTracker(
    val id: Int,
    val name: String
)

@Serializable
data class TaskStatus (
    val id: Int,
    val name: String,
    val is_closed: Boolean? = null
)

@Serializable
data class TaskPriority (
    val id: Int,
    val name: String
)

@Serializable
data class TaskAssignedTo(
    val id: Int,
    val name: String
)

@Serializable
data class TaskPrioritiesResponse (
    val issue_priorities: List<TaskPriorityDetail>
)

@Serializable
data class TaskPriorityDetail(
    val id: Int,
    val name: String,
    val is_default: Boolean,
    val active: Boolean
)

data class TaskAttribute (
    val id: Int? = null,
    val title: String? = null,
    val name: String
)