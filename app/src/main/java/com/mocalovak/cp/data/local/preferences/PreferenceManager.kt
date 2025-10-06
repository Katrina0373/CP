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

// At the top level of your kotlin file:
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
){

    companion object{
        private val IS_CHARACTER_IMPROVED = booleanPreferencesKey("is_character_improved")
        private val IS_SKILLS_IMPORTED = booleanPreferencesKey("is_skills_imported")
        private val IS_EQUIPMENT_IMPORTED = booleanPreferencesKey("is_equipment_imported")
        private var LAST_CHARACTER_ID = stringPreferencesKey("last_character_id")
    }

    val isCharactersImproved: Flow<Boolean> = context.dataStore.data
        .map {prefs -> prefs[IS_CHARACTER_IMPROVED] ?: false}

    var lastCharacterId: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[LAST_CHARACTER_ID] ?: "null" }

    suspend fun setCharacterImproved(){
        context.dataStore.edit {prefs ->
            prefs[IS_CHARACTER_IMPROVED] = true
        }
    }

    suspend fun setLastCharacterId(id:String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_CHARACTER_ID] = id
        }
    }

    val isSkillsImported: Flow<Boolean> = context.dataStore.data
        .map{prefs -> prefs[IS_SKILLS_IMPORTED] ?: false}

    suspend fun setSkillsImported(){
        context.dataStore.edit { prefs->
            prefs[IS_SKILLS_IMPORTED] = true
        }
    }

    val isEquipmentImported: Flow<Boolean> = context.dataStore.data
        .map{prefs -> prefs[IS_EQUIPMENT_IMPORTED] ?: false}

    suspend fun setEquipmentImported(){
        context.dataStore.edit { prefs->
            prefs[IS_EQUIPMENT_IMPORTED] = true
        }
    }

    suspend fun deleteLastCharacterId() {
        context.dataStore.edit { prefs ->
            prefs[LAST_CHARACTER_ID] = "null"
        }
    }
}