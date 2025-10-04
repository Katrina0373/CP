package com.mocalovak.cp.domain.repository

import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository {
    fun getAllEquipment(): Flow<List<Equipment>>
    fun getCharactersEquipment(characterId: String): Flow<List<Equipment>>
    fun getEquipmentByType(type: EquipType): Flow<List<Equipment>>
}