package com.mocalovak.cp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mocalovak.cp.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CharacterDao{
    @Query("select * from characters")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("select * from characters as Ch where Ch.id = :id")
    fun getCharacterId(id: String): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Delete
    suspend fun deleteOne(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(character:CharacterEntity)
}