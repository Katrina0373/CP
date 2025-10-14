package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(character: Character) {
        characterRepository.updateCharacter(character)
    }

    fun updateGold(characterId:Int, newValue: Int) {
        characterRepository.updateGold(characterId, newValue)
    }

    fun updateHP(characterId:Int, newValue: Int) {
        characterRepository.updateHP(characterId, newValue)
    }

    fun updateMana(characterId:Int, newValue: Int) {
        characterRepository.updateMana(characterId, newValue)
    }

    fun updateLanguages(characterId:Int, newValue: List<String>) {
        characterRepository.updateLanguages(characterId, newValue)
    }

    fun levelUp(characterId:Int) {
        characterRepository.levelUp(characterId)
    }
}