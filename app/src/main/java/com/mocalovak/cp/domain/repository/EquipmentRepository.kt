package com.mocalovak.cp.domain.repository

import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository {
    fun getAllEquipment(): Flow<List<Equipment>>
    fun getCharactersEquipment(characterId: Int): Flow<List<Equipment>>
    fun getEquipmentByType(type: EquipType): Flow<List<Equipment>>
    fun addEquipmentCrossRef(characterId:Int, equipmentId:String)
    fun equipItem(itemId:String, slot: BodyPart)
    fun unEquipItem(itemId:String)
    fun deleteItemFromCharacter(itemId: String)
}