package com.mocalovak.cp.data.local.importer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.data.local.dao.SkillCharacterRefDao
import com.mocalovak.cp.data.local.entity.CharacterEntity
import com.mocalovak.cp.data.local.entity.SkillCharacterCrossRef
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SCRefsImporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sCRefsDao: SkillCharacterRefDao,
    private val preferenceManager: PreferenceManager
) {
    companion object {
        private const val FILE_NAME = "skill_character_refs.json"
    }

    suspend fun importIfNeeded() {
        val alreadyImported = preferenceManager.isSCRefsImported.first()
        //if (alreadyImported) return

        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<SkillCharacterCrossRef>>() {}.type
        val refs: List<SkillCharacterCrossRef> = gson.fromJson(json, listType)

        sCRefsDao.insertAll(refs)
        preferenceManager.setSCRefsImported()
    }
}