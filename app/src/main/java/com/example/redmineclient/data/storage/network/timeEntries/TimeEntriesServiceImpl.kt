package com.example.redmineclient.data.storage.network.timeEntries

import com.example.redmineclient.data.storage.network.HttpRoutes
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TimeEntriesServiceImpl(private val client: HttpClient): TimeEntriesService {
    override suspend fun getTimeEntries(api_key: String, project_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TIME_ENTRIES)
            parameter(key = "key", api_key)
            parameter(key = "project_id", project_id)
        }
    }

    override suspend fun getTimeEntry(api_key: String, time_entry_id: Int): HttpResponse {
        return client.get {
            url(HttpRoutes.GET_TIME_ENTRY + time_entry_id + HttpRoutes.TYPE)
            parameter(key = "key", api_key)
        }
    }

    override suspend fun deleteTimeEntry(api_key: String, time_entries_id: Int): HttpResponse {
        return client.delete {
            url(HttpRoutes.DELETE_TIME_ENTRIES + time_entries_id + HttpRoutes.TYPE)
            parameter(key = "key", api_key)
        }
    }

    override suspend fun putTimeEntry(
        api_key: String,
        timeEntriesRequest: TimeEntriesRequest
    ): HttpResponse {
        return client.put {
            url(HttpRoutes.PUT_TIME_ENTRIES + timeEntriesRequest.time_entry.time_entry_id + HttpRoutes.TYPE)
            parameter(key = "key", api_key)
            contentType(ContentType.Application.Json)
            setBody(timeEntriesRequest)
        }
    }

    override suspend fun postTimeEntry(
        api_key: String,
        timeEntriesRequest: TimeEntriesRequest
    ): HttpResponse {
        return client.post {
            url(HttpRoutes.POST_TIME_ENTRIES)
            parameter(key = "key", api_key)
            contentType(ContentType.Application.Json)
            setBody(timeEntriesRequest)
        }
    }
}