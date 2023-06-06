package com.example.redmineclient.data.storage.network.timeEntries

import io.ktor.client.statement.*

interface TimeEntriesService {
    suspend fun getTimeEntries(apiKey: String, project_id: Int): HttpResponse
}