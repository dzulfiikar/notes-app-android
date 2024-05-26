package com.dzul.notestapp.data.repositories

import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object ProfilePreferencesKeys {
    val PROFILE_NAME = stringPreferencesKey("profile_name")
    val PROFILE_IMAGE = stringPreferencesKey("profile_image")
}

interface ProfileRepository {
    suspend fun saveName(name: String)
    fun getName(): Flow<String>
    suspend fun saveImage(image: Uri)
    fun getImage(): Flow<Uri>
}

class ProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ProfileRepository {
    override suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[ProfilePreferencesKeys.PROFILE_NAME] = name
        }
    }

    override fun getName(): Flow<String> = dataStore.data.map { preferences ->
        preferences[ProfilePreferencesKeys.PROFILE_NAME] ?: ""
    }

    override suspend fun saveImage(image: Uri) {
        dataStore.edit { preferences ->
            preferences[ProfilePreferencesKeys.PROFILE_IMAGE] = image.normalizeScheme().toString()
        }
    }

    override fun getImage(): Flow<Uri> = dataStore.data.map { preferences ->
        preferences[ProfilePreferencesKeys.PROFILE_IMAGE]?.toUri() ?: Uri.EMPTY
    }
}