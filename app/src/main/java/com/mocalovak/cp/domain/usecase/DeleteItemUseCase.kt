package com.mocalovak.cp.domain.usecase

import androidx.room.Insert
import com.mocalovak.cp.domain.repository.EquipmentRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repo: EquipmentRepository
) {
    operator fun invoke(id:String){
        repo.deleteItemFromCharacter(id)
    }
}