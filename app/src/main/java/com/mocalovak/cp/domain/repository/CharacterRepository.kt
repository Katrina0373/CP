package com.mocalovak.cp.domain.repository

import kotlinx.coroutines.flow.Flow
import com.mocalovak.cp.domain.model.Character
import kotlinx.coroutines.flow.StateFlow

interface CharacterRepository {
    fun getCharacters():Flow<List<Character>>
    fun getCharacter(id:Int): Flow<Character>
    fun updateCharacter(character: Character)
    fun updateGold(characterId:Int, newValue:Int)
    fun updateHP(characterId:Int, newValue:Int)
    fun updateMana(characterId: Int, newValue:Int)
    fun levelUp(characterId:Int)
    fun updateLanguages(characterId: Int, languages:List<String>)
}