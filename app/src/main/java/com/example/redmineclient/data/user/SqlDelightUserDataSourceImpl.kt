package com.example.redmineclient.data.user

import com.example.sebbiamanager.database.TaskManagerDatabase
import com.example.redmineclient.domain.user.User
import com.example.redmineclient.domain.user.UserDataSource
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

class SqlDelightUserDataSourceImpl(db: TaskManagerDatabase): UserDataSource, SqlDriver {

    private val queries = db.taskmanagerQueries

    override suspend fun insertUser(user: User) {
        queries.insertUser(
            id = user.id,
            login = user.login,
            password = user.password,
            isTeamLead = user.isTeamLead
        )
    }

    override suspend fun loginUser(login: String, password: String): User? {
        return queries
            .loginUser(login, password)
            .executeAsOneOrNull()
            ?.toUser()
    }

    override suspend fun getAllUsers(): List<User> {
        return queries
            .getAllUsers()
            .executeAsList()
            .map { it.toUser() }
    }

    override suspend fun deleteUserById(id: Long) {
        queries.deleteUserById(id)
    }

    override suspend fun getCurrentUser(id: Long): User? {
        return queries
            .getCurrentUser(id)
            .executeAsOneOrNull()
            ?.toUser()
    }

    override suspend fun changeGradeUser(isTeamLead: Boolean, id: Long) {
        queries.changeGradeUser(
            isTeamLead = isTeamLead,
            id = id,
        )
    }

    override fun close() {
        TODO("Not yet implemented")
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
        binders: (SqlPreparedStatement.() -> Unit)?,
    ): SqlCursor {
        TODO("Not yet implemented")
    }

    override fun newTransaction(): Transacter.Transaction {
        TODO("Not yet implemented")
    }
}