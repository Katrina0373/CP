package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersEquipment @Inject constructor(
    private val equipmentRepository: EquipmentRepository
){
    operator fun invoke(characterId: Int): Flow<List<Equipment>>{
        return equipmentRepository.getCharactersEquipment(characterId)
    }
}