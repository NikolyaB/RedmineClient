package com.example.redmineclient.domain.gateway.timeEntries

import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse
import com.example.redmineclient.domain.models.timeEntries.TimeEntryResponse

interface TimeEntriesGateway {
    suspend fun getTimeEntries(api_key: String, project_id: Int): TimeEntriesResponse
    suspend fun getTimeEntry(api_key: String, time_entry_id: Int): TimeEntryResponse
    suspend fun deleteTimeEntries(api_key: String, time_entries_id: Int): TimeEntriesResponse
    suspend fun postTimeEntries(api_key: String, timeEntriesRequest: TimeEntriesRequest): TimeEntriesResponse
    suspend fun putTimeEntries(api_key: String, timeEntriesRequest: TimeEntriesRequest): TimeEntriesResponse
}