package com.example.redmineclient.data.local

import android.content.Context
import com.example.redmineclient.database.TaskManagerDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TaskManagerDatabase.Schema, context, "taskmanager.db")
    }
}