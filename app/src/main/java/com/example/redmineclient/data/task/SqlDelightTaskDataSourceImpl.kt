package com.example.redmineclient.data.task

import com.example.sebbiamanager.database.TaskManagerDatabase
import com.example.redmineclient.domain.task.Task
import com.example.redmineclient.domain.task.TaskDataSource
import com.example.redmineclient.domain.time.DateTimeUtil
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

class SqlDelightTaskDataSourceImpl(db: TaskManagerDatabase): TaskDataSource, SqlDriver {

    private val queries = db.taskmanagerQueries

    override suspend fun insertTask(task: Task) {
        queries.insertTask(
            id = task.id,
            title = task.title,
            content = task.content,
            priority = task.priority,
            isDone = task.isDone,
            colorHex = task.colorHex,
            created = DateTimeUtil.toEpochMillis(task.created),
            userId = task.userId
        )
    }

    override suspend fun getTaskById(id: Long): Task? {
        return queries
            .getTaskById(id)
            .executeAsOneOrNull()
            ?.toTask()
    }

    override suspend fun getAllTasks(): List<Task> {
        return queries
            .getAllTasks()
            .executeAsList()
            .map { it.toTask() }
    }

    override suspend fun deleteTaskById(id: Long) {
        queries.deleteTaskById(id)
    }

    override suspend fun getAllTasksByIsDone(userId: Long): List<Task> {
        return queries
            .getAllTasksByIsDone(userId)
            .executeAsList()
            .map { it.toTask() }
    }

    override suspend fun getAllTasksByNotIsDone(userId: Long): List<Task> {
        return queries
            .getAllTasksByNotIsDone(userId)
            .executeAsList()
            .map { it.toTask() }
    }

    override suspend fun getAllTasksByPriority(userId: Long): List<Task> {
        return queries
            .getAllTasksByPriority(userId)
            .executeAsList()
            .map { it.toTask() }
    }

    override suspend fun updateStatusTask(isDone: Boolean, id: Long) {
        queries.updateStatusTask(
            isDone = isDone,
            id = id,
        )
    }

    override suspend fun getAllTasksByUserId(id: Long): List<Task> {
        return queries
            .getAllTasksByUserId(id)
            .executeAsList()
            .map { it.toTask() }
    }

    override fun currentTransaction(): Transacter.Transaction? {
        TODO("Not yet implemented")
    }

    override fun execute(identifier: Int?, sql: String, parameters: Int, binders: (SqlPreparedStatement.() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun executeQuery(
        identifier: Int?,
        sql: String,
        parameters: Int,
        binders: (SqlPreparedStatement.() -> Unit)?
    ): SqlCursor {
        TODO("Not yet implemented")
    }

    override fun newTransaction(): Transacter.Transaction {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}