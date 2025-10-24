package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.repository.EquipmentRepository
import javax.inject.Inject

class EquipItemUseCase @Inject constructor(
    private val repo: EquipmentRepository
) {

    fun takeEmptySlots(characterEquipment: List<Equipment>, item:Equipment):List<BodyPart>{
        if(item is Equipment.Weapon){
            if(item.isEquipped != null) return emptyList()

            if(item.slot[0] == BodyPart.TwoHands){
                return if (characterEquipment.any{
                    it is Equipment.Weapon && it.isEquipped != null
                }) emptyList()
                else item.slot
            } else {
                return item.slot.filter { slot ->
                    characterEquipment.none {
                        it is Equipment.Weapon && (it.isEquipped == slot || it.isEquipped == BodyPart.TwoHands)
                    }
                }
            }
        }
        else if(item is Equipment.Clothes){
            if(item.isEquipped != null) return emptyList()

             return item.slot.filter { slot ->
                 characterEquipment.none {
                     it is Equipment.Clothes && it.isEquipped == slot
                 }
             }
        }
        else return emptyList()
    }

    fun takeEmptySlots2(characterEquipment: List<Equipment>, item: Equipment): List<BodyPart> {
        // Проверяем, поддерживает ли предмет экипировку
        val (slots, equippedPart) = when (item) {
            is Equipment.Weapon -> item.slot to item.isEquipped
            is Equipment.Clothes -> item.slot to item.isEquipped
            else -> return emptyList() // другие типы нас не интересуют
        }

        if (equippedPart != null) return emptyList()

        // свободные слоты
        return slots.filter { slot ->
            characterEquipment.none { equipped ->
                when (equipped) {
                    is Equipment.Weapon -> equipped.isEquipped == slot
                    is Equipment.Clothes -> equipped.isEquipped == slot
                    else -> false
                }
            }
        }
    }


    fun unEquipItem(itemId:String){
        repo.unEquipItem(itemId)
    }

    operator fun invoke(itemId: String, slot: BodyPart){
        repo.equipItem(itemId, slot)
    }
}