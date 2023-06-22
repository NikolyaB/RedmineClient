package com.example.redmineclient.domain.usecase.timeEntries

import com.example.redmineclient.domain.gateway.timeEntries.TimeEntriesGateway
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse
import com.example.redmineclient.domain.models.timeEntries.TimeEntryResponse

class TimeEntriesUseCase(private val timeEntriesGateway: TimeEntriesGateway) {
    suspend fun getTimeEntries(api_key: String, project_id: Int): TimeEntriesResponse =
        timeEntriesGateway.getTimeEntries(api_key, project_id)
    suspend fun getTimeEntry(api_key: String, time_entry_id: Int): TimeEntryResponse =
        timeEntriesGateway.getTimeEntry(api_key,time_entry_id)
    suspend fun deleteTimeEntries(api_key: String, time_entries_id: Int): TimeEntriesResponse =
        timeEntriesGateway.deleteTimeEntries(api_key, time_entries_id)
    suspend fun putTimeEntries(api_key: String, timeEntriesRequest: TimeEntriesRequest): TimeEntriesResponse =
        timeEntriesGateway.putTimeEntries(api_key, timeEntriesRequest)
    suspend fun postTimeEntries(api_key: String, timeEntriesRequest: TimeEntriesRequest): TimeEntriesResponse =
        timeEntriesGateway.postTimeEntries(api_key, timeEntriesRequest)
}