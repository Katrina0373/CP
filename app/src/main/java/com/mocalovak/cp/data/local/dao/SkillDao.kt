package com.mocalovak.cp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mocalovak.cp.data.local.entity.SkillCharacterCrossRef
import com.mocalovak.cp.data.local.entity.SkillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {
    @Query("select * from skills")
    fun getAll():Flow<List<SkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(skills: List<SkillEntity>)
}

@Dao
interface SkillCharacterRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(refs: List<SkillCharacterCrossRef>)
}