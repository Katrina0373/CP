package com.mocalovak.cp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    private object Keys {
        val IS_CHARACTER_IMPORTED = booleanPreferencesKey("is_character_imported")
        val IS_SKILLS_IMPORTED = booleanPreferencesKey("is_skills_imported")
        val IS_EQUIPMENT_IMPORTED = booleanPreferencesKey("is_equipment_imported")
        val IS_ECREFS_IMPORTED = booleanPreferencesKey("is_equipment_characters_refs_imported")
        val IS_SCREFS_IMPORTED = booleanPreferencesKey("is_skill_character_refs_imported")
        val LAST_CHARACTER_ID = stringPreferencesKey("last_character_id")
    }

    // ---------- Универсальные функции ----------------------------------------

    inline fun <reified T> getPreferenceFlow(
        key: Preferences.Key<T>,
        default: T
    ): Flow<T> = context.dataStore.data.map { it[key] ?: default }

    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { prefs -> prefs[key] = value }
    }

    // ---------- Готовые поля --------------------------------------------------

    val isCharacterImported = getPreferenceFlow(Keys.IS_CHARACTER_IMPORTED, false)
    val isSkillsImported = getPreferenceFlow(Keys.IS_SKILLS_IMPORTED, false)
    val isEquipmentImported = getPreferenceFlow(Keys.IS_EQUIPMENT_IMPORTED, false)
    val isECRefsImported = getPreferenceFlow(Keys.IS_ECREFS_IMPORTED, false)
    val isSCRefsImported = getPreferenceFlow(Keys.IS_SCREFS_IMPORTED, false)

    val lastCharacterId = getPreferenceFlow(Keys.LAST_CHARACTER_ID, "null")

    // ---------- Готовые методы ------------------------------------------------

    suspend fun setCharacterImported() = setPreference(Keys.IS_CHARACTER_IMPORTED, true)
    suspend fun setSkillsImported() = setPreference(Keys.IS_SKILLS_IMPORTED, true)
    suspend fun setEquipmentImported() = setPreference(Keys.IS_EQUIPMENT_IMPORTED, true)
    suspend fun setECRefsImported() = setPreference(Keys.IS_ECREFS_IMPORTED, true)
    suspend fun setSCRefsImported() = setPreference(Keys.IS_SCREFS_IMPORTED, true)

    suspend fun setLastCharacterId(id: String) = setPreference(Keys.LAST_CHARACTER_ID, id)
    suspend fun deleteLastCharacterId() = setPreference(Keys.LAST_CHARACTER_ID, "null")
}
