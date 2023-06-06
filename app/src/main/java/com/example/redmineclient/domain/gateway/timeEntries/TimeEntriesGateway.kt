package com.example.redmineclient.domain.gateway.timeEntries

import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse

interface TimeEntriesGateway {
    suspend fun getTimeEntries(apiKey: String, project_id: Int): TimeEntriesResponse
}