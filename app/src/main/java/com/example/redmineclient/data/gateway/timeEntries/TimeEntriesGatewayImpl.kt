package com.example.redmineclient.data.gateway.timeEntries

import com.example.redmineclient.data.storage.network.timeEntries.TimeEntriesServiceImpl
import com.example.redmineclient.domain.gateway.timeEntries.TimeEntriesGateway
import com.example.redmineclient.domain.models.timeEntries.TimeEntries
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesResponse
import com.example.redmineclient.domain.models.tasks.Tasks
import com.example.redmineclient.domain.state.StatusResponse
import io.ktor.client.call.*
import io.ktor.http.*

class TimeEntriesGatewayImpl(private val timeEntriesServiceImpl: TimeEntriesServiceImpl): TimeEntriesGateway {
    override suspend fun getTimeEntries(apiKey: String, project_id: Int): TimeEntriesResponse {
        val response = timeEntriesServiceImpl.getTimeEntries(apiKey = apiKey, project_id = project_id)
        return when (response.status) {
            HttpStatusCode.OK -> {
                println("response TimeEntries ${response.status}")
                println("RESPONSE TimeEntries ${response.body<TimeEntries>()}")
                TimeEntriesResponse(timeEntries = response.body<TimeEntries>(), statusResponse = StatusResponse.OK)
            }
            else -> {
                TimeEntriesResponse(statusResponse = StatusResponse.Error)
            }
        }
    }
}