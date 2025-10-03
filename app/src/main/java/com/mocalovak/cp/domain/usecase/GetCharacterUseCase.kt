package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(id:String): Flow<Character?> {
        return characterRepository.getCharacter(id)
    }
}