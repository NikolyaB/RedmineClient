package com.example.redmineclient.domain.state

enum class StatusResponse(error: Throwable? = null) {
    OK,
    Created,
    NoContent,
    BadRequest,
    Unauthorized,
    BadGateway,
    Error
}