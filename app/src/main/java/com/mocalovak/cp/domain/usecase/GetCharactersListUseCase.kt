package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<List<Character>>{
        return repository.getCharacters()
    }
}