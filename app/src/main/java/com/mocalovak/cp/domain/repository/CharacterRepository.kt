package com.mocalovak.cp.domain.repository

import kotlinx.coroutines.flow.Flow
import com.mocalovak.cp.domain.model.Character
import kotlinx.coroutines.flow.StateFlow

interface CharacterRepository {
    fun getCharacters():Flow<List<Character>>
    fun getCharacter(id:String): Flow<Character?>
    fun updateCharacter(character: Character)
    fun updateGold(characterId:String, newValue:Int)
    fun updateHP(characterId:String, newValue:Int)
    fun updateMana(characterId:String, newValue:Int)
    fun levelUp(characterId:String)
}