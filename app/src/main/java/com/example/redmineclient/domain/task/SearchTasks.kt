package com.example.redmineclient.domain.task

import com.example.redmineclient.domain.time.DateTimeUtil

class SearchTasks {
    fun execute(tasks: List<Task>, query: String): List<Task> {

        if (query.isBlank()) return tasks

        return tasks.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}