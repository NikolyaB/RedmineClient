package com.example.redmineclient.data.task

import com.example.redmineclient.domain.task.Task
import database.TaskEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        content = content,
        priority = priority,
        isDone = isDone,
        colorHex = colorHex,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        userId = userId
    )
}