package com.example.redmineclient.data.storage.network

object HttpRoutes {
    private const val JSON = ".json"
    private const val XML = ".xml"
    private const val TYPE = JSON
    private const val API_URL: String = "https://redmine.apps.okd.sebbia.org"
    // User
    const val GET_CURRENT_USER = "$API_URL/users/current$TYPE"
    // Projects
    const val GET_PROJECTS = "$API_URL/projects$TYPE"
    // Tasks
    const val GET_TASKS = "$API_URL/issues$TYPE"
    // Time_Entries
    const val GET_TIME_ENTRIES = "$API_URL/time_entries$TYPE"
}