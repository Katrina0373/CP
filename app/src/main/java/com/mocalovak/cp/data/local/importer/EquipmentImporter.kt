package com.mocalovak.cp.data.local.importer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.entity.EquipmentEntity
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.domain.model.Equipment
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipmentImporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val equipmentDao: EquipmentDao,
    private val preferenceManager: PreferenceManager
) {

    companion object {
        private const val FILE_NAME = "equipment.json"
    }

    suspend fun importIfNeeded(){
        val alreadyImported = preferenceManager.isEquipmentImported.first()
        if (alreadyImported) return

        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<EquipmentEntity>>() {}.type
        val equipment: List<EquipmentEntity> = gson.fromJson(json, listType)

        equipmentDao.insertAll(equipment)
        preferenceManager.setEquipmentImported()

    }

}