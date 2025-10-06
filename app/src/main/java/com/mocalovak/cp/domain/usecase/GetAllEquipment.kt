package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEquipment @Inject constructor(
    private val repository: EquipmentRepository
) {
    operator fun invoke(): Flow<List<Equipment>>{
        return repository.getAllEquipment()
    }
}