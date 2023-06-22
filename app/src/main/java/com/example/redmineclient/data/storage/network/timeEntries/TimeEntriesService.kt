package com.example.redmineclient.data.storage.network.timeEntries

import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import io.ktor.client.statement.HttpResponse

interface TimeEntriesService {
    suspend fun getTimeEntries(api_key: String, project_id: Int): HttpResponse
    suspend fun getTimeEntry(api_key: String, time_entry_id: Int): HttpResponse
    suspend fun deleteTimeEntry(api_key: String, time_entries_id: Int): HttpResponse
    suspend fun putTimeEntry(api_key: String, timeEntriesRequest: TimeEntriesRequest): HttpResponse
    suspend fun postTimeEntry(api_key: String, timeEntriesRequest: TimeEntriesRequest): HttpResponse
}