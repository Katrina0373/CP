package com.mocalovak.cp.data.repository

import com.mocalovak.cp.data.local.dao.EquipmentCharacterRefDao
import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.entity.CharacterEquipmentCrossRef
import com.mocalovak.cp.data.local.entity.toDomain
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val dao: EquipmentDao,
    private val daoRef: EquipmentCharacterRefDao
):EquipmentRepository {
    override fun getAllEquipment(): Flow<List<Equipment>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getCharactersEquipment(characterId: Int): Flow<List<Equipment>> { //у этого Equipment вместо id из equipment id из character_equipment_cross_ref
        return dao.getCharactersEquipment(characterId)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getEquipmentByType(type: EquipType): Flow<List<Equipment>> {
        return dao.getEquipByType(type)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun addEquipmentCrossRef(characterId: Int, equipmentId: String) {
        daoRef.insertOne(CharacterEquipmentCrossRef(characterId = characterId, equipmentId = equipmentId, isEquipped = null))
    }

    override fun equipItem(itemId: String, slot: BodyPart) {
        daoRef.updateEquipStatus(itemId.toInt(), slot)
    }

    override fun unEquipItem(itemId: String) {
        daoRef.updateEquipStatus(itemId.toInt(), null)
    }

    override fun deleteItemFromCharacter(itemId: String) {
        daoRef.deleteEquipCharacterCrossRef(itemId.toInt())
    }
}