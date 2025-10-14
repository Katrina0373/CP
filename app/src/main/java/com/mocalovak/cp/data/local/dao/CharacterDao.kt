package com.mocalovak.cp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mocalovak.cp.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CharacterDao{
    @Query("select * from characters")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("select * from characters as Ch where Ch.id = :id")
    fun getCharacterId(id: Int): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Delete
    fun deleteOne(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(character:CharacterEntity)

    @Query("UPDATE characters SET gold = :gold WHERE id = :id")
    fun updateGold(id: Int, gold: Int)

    @Query("UPDATE characters SET currentHP = :hp WHERE id = :id")
    fun updateHP(id: Int, hp: Int)

    @Query("UPDATE characters SET currentMana = :mana WHERE id = :id")
    fun updateMana(id: Int, mana: Int)

    @Query("update characters set level = level+1 where id = :id")
    fun levelUp(id:Int)


    @Query("update characters set languages = :languages where id = :id")
    fun updateLanguages(id:Int, languages:List<String>)

    @Update
    fun updateCharacter(character: CharacterEntity)
}