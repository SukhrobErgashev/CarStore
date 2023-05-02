package dev.sukhrob.carstore.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.UIModePref by preferencesDataStore("ui_mode")

class UiSourceImpl @Inject constructor(@ApplicationContext context: Context): UiSource {

    private val dataStore = context.UIModePref

    override val justInstalled: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            val state = preferences[KEY_JUST_INSTALLED]
            state ?: true
        }

    override suspend fun setJustInstalled(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_JUST_INSTALLED] = state
        }
    }

    companion object {
        val KEY_JUST_INSTALLED = booleanPreferencesKey("just_installed")
    }
}