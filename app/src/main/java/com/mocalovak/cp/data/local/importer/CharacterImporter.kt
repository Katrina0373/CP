package com.mocalovak.cp.data.local.importer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.entity.CharacterEntity
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterImporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val characterDao: CharacterDao,
    private val preferenceManager: PreferenceManager
) {
    companion object {
        private const val FILE_NAME = "characters.json"
    }

    suspend fun importIfNeeded() {
        val alreadyImported = preferenceManager.isCharactersImproved.first()
        if (alreadyImported) return

        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<CharacterEntity>>() {}.type
        val characters: List<CharacterEntity> = gson.fromJson(json, listType)

        characterDao.insertAll(characters)
        preferenceManager.setCharacterImproved()
    }
}
