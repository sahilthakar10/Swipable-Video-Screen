package com.sahil.tiktok.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "login"
        )
    }

    val bookmark: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_BOOKMARK]
        }

    suspend fun saveBookmark(bookmark: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_BOOKMARK] = bookmark
        }
    }


    companion object {
        val KEY_BOOKMARK = preferencesKey<Boolean>("verify")
    }
}