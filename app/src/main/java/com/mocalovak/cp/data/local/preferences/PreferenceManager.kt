package com.mocalovak.cp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.datastore.core.DataMigration

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings",
//    produceMigrations = { context ->
//        listOf(object : DataMigration<Preferences> {
//            // ключи старого и нового типа
//            val OLD_KEY = stringPreferencesKey("last_character_id")
//            val NEW_KEY = intPreferencesKey("last_character_id")
//
//            override suspend fun shouldMigrate(currentData: Preferences): Boolean {
//                // мигрируем, если старый ключ присутствует
//                return currentData.contains(OLD_KEY)
//            }

//            override suspend fun migrate(currentData: Preferences): Preferences {
//                // получаем изменяемую копию
//                val mutable = currentData.toMutablePreferences()
//
//                val oldValue = currentData[OLD_KEY]
//                if (oldValue != null) {
//                    // пробуем безопасно конвертировать
//                    val intValue = oldValue.toIntOrNull()
//                    if (intValue != null) {
//                        mutable[NEW_KEY] = intValue
//                    } else {
//                        // fallback: можно записать -1 или удалить ключ
//                        mutable[NEW_KEY] = 0
//                    }
//                    // удаляем старый string-ключ
//                    mutable.remove(OLD_KEY)
//                }
//                return mutable.toPreferences()
//            }

//            override suspend fun cleanUp() {
//                // не обязательно что-то делать
//            }
//        })
//    }
)


class PreferenceManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    private object Keys {
        val IS_CHARACTER_IMPORTED = booleanPreferencesKey("is_character_imported")
        val IS_SKILLS_IMPORTED = booleanPreferencesKey("is_skills_imported")
        val IS_EQUIPMENT_IMPORTED = booleanPreferencesKey("is_equipment_imported")
        val IS_ECREFS_IMPORTED = booleanPreferencesKey("is_equipment_characters_refs_imported")
        val IS_SCREFS_IMPORTED = booleanPreferencesKey("is_skill_character_refs_imported")
        val LAST_CHARACTER_ID = intPreferencesKey("last_character_id")
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

    val lastCharacterId = getPreferenceFlow(Keys.LAST_CHARACTER_ID, 0)

    // ---------- Готовые методы ------------------------------------------------

    suspend fun setCharacterImported() = setPreference(Keys.IS_CHARACTER_IMPORTED, true)
    suspend fun setSkillsImported() = setPreference(Keys.IS_SKILLS_IMPORTED, true)
    suspend fun setEquipmentImported() = setPreference(Keys.IS_EQUIPMENT_IMPORTED, true)
    suspend fun setECRefsImported() = setPreference(Keys.IS_ECREFS_IMPORTED, true)
    suspend fun setSCRefsImported() = setPreference(Keys.IS_SCREFS_IMPORTED, true)

    suspend fun setLastCharacterId(id: Int) = setPreference(Keys.LAST_CHARACTER_ID, id)
    suspend fun deleteLastCharacterId() = setPreference(Keys.LAST_CHARACTER_ID, 0)
}
