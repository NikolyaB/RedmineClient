package com.example.redmineclient.domain.task

import com.example.redmineclient.domain.task.Task

interface TaskDataSource {
    suspend fun insertTask(task: Task)
    suspend fun getTaskById(id: Long): Task?
    suspend fun getAllTasks(): List<Task>
    suspend fun deleteTaskById(id: Long)
    suspend fun getAllTasksByIsDone(userId: Long): List<Task>
    suspend fun getAllTasksByNotIsDone(userId: Long): List<Task>
    suspend fun getAllTasksByPriority(userId: Long): List<Task>
    suspend fun updateStatusTask(isDone: Boolean, id: Long)
    suspend fun getAllTasksByUserId(id: Long): List<Task>
}