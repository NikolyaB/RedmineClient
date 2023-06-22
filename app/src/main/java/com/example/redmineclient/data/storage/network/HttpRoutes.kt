package com.example.redmineclient.data.storage.network

object HttpRoutes {
    private const val JSON = ".json"
    private const val XML = ".xml"
    const val TYPE = JSON
    private const val API_URL: String = "https://redmine.apps.okd.sebbia.org"
    // User
    const val GET_CURRENT_USER = "$API_URL/users/current$TYPE"
    // Projects
    const val GET_PROJECTS = "$API_URL/projects$TYPE"
    // Tasks
    const val GET_TASKS = "$API_URL/issues$TYPE"
    const val GET_TASK = "$API_URL/issues$TYPE"
    const val PUT_TASK = "$API_URL/issues/"
    const val POST_TASK = "$API_URL/issues$TYPE"
    // Time_Entries
    const val GET_TIME_ENTRIES = "$API_URL/time_entries$TYPE"
    const val GET_TIME_ENTRY = "$API_URL/time_entries/"
    const val DELETE_TIME_ENTRIES = "$API_URL/time_entries/"
    const val POST_TIME_ENTRIES = "$API_URL/time_entries$TYPE"
    const val PUT_TIME_ENTRIES = "$API_URL/time_entries/"
}