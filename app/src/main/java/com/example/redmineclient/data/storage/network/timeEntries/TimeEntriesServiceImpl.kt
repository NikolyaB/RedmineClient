package com.example.redmineclient.data.storage.network.timeEntries

import com.example.redmineclient.data.storage.network.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class TimeEntriesServiceImpl(private val client: HttpClient): TimeEntriesService {
    override suspend fun getTimeEntries(apiKey: String, project_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TIME_ENTRIES)
            parameter(key = "key", apiKey)
            parameter(key = "project_id", project_id)
        }
    }
}