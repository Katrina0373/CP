package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.repository.EquipmentRepository
import javax.inject.Inject

class AddEquipmentUseCase @Inject constructor(
    private val repo: EquipmentRepository
) {
    operator fun invoke(characterId:Int, equipmentId:String){
        repo.addEquipmentCrossRef(characterId, equipmentId)
    }
}