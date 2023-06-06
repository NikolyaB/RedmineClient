package com.example.redmineclient.domain.models.timeEntries

import com.example.redmineclient.domain.state.StatusResponse
import kotlinx.serialization.Serializable

@Serializable
data class TimeEntriesResponse(
    val timeEntries: TimeEntries? = null,
    val statusResponse: StatusResponse
)

@Serializable
data class TimeEntries(
    val time_entries: List<TimeEntriesInfo>,
)

@Serializable
data class TimeEntriesInfo(
    val id: Int,
    val project: ProjectTimeEntries,
    val issue: IssueTimeEntries,
    val user: UserTimeEntries,
    val activity: ActivityTimeEntries,
    val hours: Double,
    val comments: String,
    val spent_on: String,
)

@Serializable
data class ProjectTimeEntries(
    val id: Int,
    val name: String
)

@Serializable
data class IssueTimeEntries(
    val id: Int
)

@Serializable
data class UserTimeEntries(
    val id: Int,
    val name: String
)

@Serializable
data class ActivityTimeEntries (
    val id: Int,
    val name: String
)