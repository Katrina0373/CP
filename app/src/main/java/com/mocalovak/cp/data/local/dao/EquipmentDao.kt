package com.mocalovak.cp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mocalovak.cp.data.local.entity.CharacterEquipmentCrossRef
import com.mocalovak.cp.data.local.entity.EquipmentEntity
import com.mocalovak.cp.data.local.entity.EquipmentWithStatus
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Query("select * from equipment")
    fun getAll(): Flow<List<EquipmentEntity>>

    @Query("""
        select c.itemId, e.*, c.isEquipped
        from equipment e
        join character_equipment_cross_ref c 
        on e.id = c.equipmentId
        where  c.characterId = :characterId""")
    fun getCharactersEquipment(characterId:Int): Flow<List<EquipmentWithStatus>>

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

    @Query("UPDATE character_equipment_cross_ref SET isEquipped = :isEquipped WHERE itemId = :id")
    fun updateEquipStatus(id: Int, isEquipped: BodyPart?) //это id из Equipment

    @Query("delete from character_equipment_cross_ref where itemId = :id")
    fun deleteEquipCharacterCrossRef(id: Int) //это id из Equipment
}