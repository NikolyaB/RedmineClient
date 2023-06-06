package com.example.redmineclient.data.storage.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val STORE_NAME = "RedmineClient"

class PreferencesStoreImpl(private val context: Context): PreferencesStore {
    private val Context.dataContext: DataStore<Preferences> by preferencesDataStore(STORE_NAME)
    override suspend fun saveToken(token: String) {
        context.dataContext.edit { prefs ->
            prefs[PreferencesKeys.TOKEN_KEY] = token
        }
    }

    override suspend fun deleteToken() {
        context.dataContext.edit { prefs ->
            prefs.clear()
        }
    }

    override fun getToken(): Flow<String> = context.dataContext.data.catch { exception ->
        if (exception is IOException){
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.TOKEN_KEY] ?: "" }

    private object PreferencesKeys {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
    }
}