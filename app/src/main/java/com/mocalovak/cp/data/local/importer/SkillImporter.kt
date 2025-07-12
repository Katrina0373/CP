package com.mocalovak.cp.data.local.importer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.entity.CharacterEntity
import com.mocalovak.cp.data.local.entity.SkillEntity
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillImporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val skillDao: SkillDao,
    private val preferenceManager: PreferenceManager
) {
    companion object {
        private const val FILE_NAME = "skill.json"
    }

    suspend fun importIfNeeded() {
        val alreadyImported = preferenceManager.isSkillsImported.first()
        if (alreadyImported) return

        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<SkillEntity>>() {}.type
        val skills: List<SkillEntity> = gson.fromJson(json, listType)

        skillDao.insertAll(skills)
        preferenceManager.setCharacterImproved()
    }
}