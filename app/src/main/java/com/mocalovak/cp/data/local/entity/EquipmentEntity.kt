package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import org.jetbrains.annotations.NotNull
import javax.annotation.processing.Generated

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id:String,
    val name:String,
    val type: EquipType,
    val slot: BodyPart,
    val description:String,

    val damage:String? = null,
    val distance: Int? = null,
    val inTwoHands: Boolean = false,
    val passiveEffect:String? = null,
    val chance:Float? = null
)

@Entity(
    tableName = "character_equipment_cross_ref",
    primaryKeys = ["characterId", "equipmentId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EquipmentEntity::class,
            parentColumns = ["id"],
            childColumns = ["equipmentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("characterId"), Index("equipmentId")]
)
data class CharacterEquipmentCrossRef(
    val characterId: String,
    val equipmentId: String,
    val isEquipped: Boolean
)
