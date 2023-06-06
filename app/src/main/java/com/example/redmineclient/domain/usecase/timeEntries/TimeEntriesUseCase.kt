package com.example.redmineclient.domain.usecase.timeEntries

import com.example.redmineclient.domain.gateway.timeEntries.TimeEntriesGateway
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse

class TimeEntriesUseCase(private val timeEntriesGateway: TimeEntriesGateway) {
    suspend fun getTimeEntries(apiKey: String, project_id: Int): TimeEntriesResponse =
        timeEntriesGateway.getTimeEntries(apiKey, project_id)
}