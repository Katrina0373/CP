package com.mocalovak.cp.data.local.importer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.data.local.dao.EquipmentCharacterRefDao
import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.entity.CharacterEquipmentCrossRef
import com.mocalovak.cp.data.local.entity.EquipmentEntity
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ECCrossRefImporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val eCCrossRefDao: EquipmentCharacterRefDao,
    private val preferenceManager: PreferenceManager
) {

    companion object {
        private const val FILE_NAME = "equip_character_cross_ref.json"
    }

    suspend fun importIfNeeded(){
        val alreadyImported = preferenceManager.isECRefsImported.first()
        if (alreadyImported) return

        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<CharacterEquipmentCrossRef>>() {}.type
        val refs: List<CharacterEquipmentCrossRef> = gson.fromJson(json, listType)

        eCCrossRefDao.insertAll(refs)
        preferenceManager.setECRefsImported()

    }

}