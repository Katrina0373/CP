package com.mocalovak.cp.domain.repository

import kotlinx.coroutines.flow.Flow
import com.mocalovak.cp.domain.model.Character

interface CharacterRepository {
    fun getCharacters():Flow<List<Character>>
    fun getCharacter(id:String): Flow<Character>
}