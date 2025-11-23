package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.activatePassiveEffects
import com.mocalovak.cp.domain.repository.CharacterRepository
import com.mocalovak.cp.domain.repository.EquipmentRepository
import com.mocalovak.cp.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val equipmentRepository: EquipmentRepository,
    private val skillRepository: SkillRepository
) {
    operator fun invoke(id: Int, withPassives:Boolean = true): Flow<Character> {

        val characterFlow = characterRepository.getCharacter(id)

        // Объединяем все три потока
        if(withPassives) {
            val equipmentFlow = equipmentRepository.getCharactersEquipment(id)
            val skillsFlow = skillRepository.getCharactersSkills(id)
            return combine(
                characterFlow,
                equipmentFlow,
                skillsFlow
            ) { character, equipment, skills ->
                activatePassiveEffects(character, equipment, skills)
            }
        }
        else {
            return characterFlow
        }
    }
}
