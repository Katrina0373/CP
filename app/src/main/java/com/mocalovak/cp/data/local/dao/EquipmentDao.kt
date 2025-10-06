package com.mocalovak.cp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mocalovak.cp.data.local.entity.CharacterEquipmentCrossRef
import com.mocalovak.cp.data.local.entity.EquipmentEntity
import com.mocalovak.cp.data.local.entity.EquipmentWithStatus
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Query("select * from equipment")
    fun getAll(): Flow<List<EquipmentEntity>>

    @Query("""
        select e.*, c.isEquipped  
        from equipment e 
        inner join character_equipment_cross_ref c 
        where  c.characterId = :characterId""")
    fun getCharactersEquipment(characterId:String): Flow<List<EquipmentWithStatus>>

    @Query("select * from equipment where name == :name")
    fun getEquipByName(name:String): Flow<List<EquipmentEntity>>


    @Query("select * from equipment where type == :type")
    fun getEquipByType(type:EquipType): Flow<List<EquipmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(equipment: List<EquipmentEntity>)
}

@Dao
interface EquipmentCharacterRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(ref: CharacterEquipmentCrossRef)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ref: List<CharacterEquipmentCrossRef>)
}