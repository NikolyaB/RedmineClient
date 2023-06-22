package com.example.redmineclient.data.gateway.timeEntries

import com.example.redmineclient.data.storage.network.timeEntries.TimeEntriesServiceImpl
import com.example.redmineclient.domain.gateway.timeEntries.TimeEntriesGateway
import com.example.redmineclient.domain.models.timeEntries.TimeEntries
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse
import com.example.redmineclient.domain.models.timeEntries.TimeEntryResponse
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class TimeEntriesGatewayImpl(private val timeEntriesServiceImpl: TimeEntriesServiceImpl): TimeEntriesGateway {
    override suspend fun getTimeEntries(api_key: String, project_id: Int): TimeEntriesResponse {
        val response = timeEntriesServiceImpl.getTimeEntries(api_key = api_key, project_id = project_id)
        return when (response.status) {
            HttpStatusCode.OK -> {
                TimeEntriesResponse(timeEntries = response.body<TimeEntries>(), statusResponse = StatusResponse.OK)
            }
            else -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun getTimeEntry(api_key: String, time_entry_id: Int): TimeEntryResponse {
        val response = timeEntriesServiceImpl.getTimeEntry(api_key = api_key, time_entry_id = time_entry_id)
        return when (response.status) {
            HttpStatusCode.OK -> {
                TimeEntryResponse(timeEntry = response.body<TimeEntries>().time_entries.component1(), statusResponse = StatusResponse.OK)
            }
            else -> {
                TimeEntryResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun deleteTimeEntries(
        api_key: String,
        time_entries_id: Int
    ): TimeEntriesResponse {
        val response = timeEntriesServiceImpl
            .deleteTimeEntry(api_key =  api_key, time_entries_id = time_entries_id)
        return when (response.status) {
            HttpStatusCode.NoContent -> {
                TimeEntriesResponse(statusResponse = StatusResponse.NoContent)
            }
            else -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun postTimeEntries(
        api_key: String,
        timeEntriesRequest: TimeEntriesRequest
    ): TimeEntriesResponse {
        val response = timeEntriesServiceImpl
            .postTimeEntry(api_key = api_key, timeEntriesRequest = timeEntriesRequest)
        return when (response.status) {
            HttpStatusCode.Created -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Created)
            }
            else -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Error)
            }
        }
    }

    override suspend fun putTimeEntries(
        api_key: String,
        timeEntriesRequest: TimeEntriesRequest
    ): TimeEntriesResponse {
        val response = timeEntriesServiceImpl
            .putTimeEntry(api_key = api_key, timeEntriesRequest = timeEntriesRequest)
        return when (response.status) {
            HttpStatusCode.NoContent -> {
                TimeEntriesResponse(statusResponse = StatusResponse.NoContent)
            }
            else -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}