package com.example.redmineclient.data.storage.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesStore {
    suspend fun saveToken(token: String)
    suspend fun deleteToken()
    fun getToken(): Flow<String>
}