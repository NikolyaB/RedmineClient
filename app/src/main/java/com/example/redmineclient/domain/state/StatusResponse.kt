package com.example.redmineclient.domain.state

enum class StatusResponse(error: Throwable? = null) {
    OK,
    BadRequest,
    Unauthorized,
    BadGateway,
    Error
}