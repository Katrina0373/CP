package com.mocalovak.cp.data.repository

import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.entity.toDomain
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val dao: EquipmentDao
):EquipmentRepository {
    override fun getAllEquipment(): Flow<List<Equipment>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getCharactersEquipment(characterId: String): Flow<List<Equipment>> {
        return dao.getCharactersEquipment(characterId)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getEquipmentByType(type: EquipType): Flow<List<Equipment>> {
        return dao.getEquipByType(type)
            .map { list -> list.map { it.toDomain() } }
    }

}