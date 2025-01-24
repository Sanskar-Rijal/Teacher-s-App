package com.example.teacherapp.caching

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_details")

class DataStoreManager(private val context: Context) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    // Save data to DataStore
    suspend fun saveUserDetails(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
        }
    }

    // Retrieve data from DataStore
    val userName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY]
        }

    val userEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY]
        }
}
