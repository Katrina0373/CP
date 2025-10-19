package com.mocalovak.cp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.PassiveEffect

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey
    val id:String,
    val name:String,
    val type: EquipType,
    val tir: Int?,
    val slot: List<BodyPart?> = emptyList(),
    val description:String,
    val weight: ArmorWeight?,

    val damage:String? = null,
    val distance: Int? = null,
    val effect:String? = null,
    val chance:Float? = null,
    val passiveEffects: List<PassiveEffect>? = null,
)

data class EquipmentWithStatus(
    val itemId: Int,
    @Embedded val equipment: EquipmentEntity,
    val isEquipped: BodyPart? = null,
)


@Entity(
    tableName = "character_equipment_cross_ref",
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
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val characterId: Int,
    val equipmentId: String,
    val isEquipped: BodyPart?
)

fun EquipmentEntity.toDomain(): Equipment {
    return when (type) {
        EquipType.Weapon -> Equipment.Weapon(
            id = id,
            name = name,
            description = description,
            damage = damage ?: "",
            slot = slot.map { it!! },
            passiveEffects = passiveEffects,
            activeEffect = effect,
            chance = chance,
            tir = tir
        )
        EquipType.Armor -> Equipment.Clothes(
            id = id,
            name = name,
            description = description,
            slot = slot.map { it!! },
            passiveEffects = passiveEffects,
            //isEquipped = isEquipped,
            armorWeight = weight,
            tir = tir
        )
        EquipType.Potion -> Equipment.Potion(
            id = id,
            name = name,
            description = description,
            effect = effect ?: "",
            tir = tir
        )
        EquipType.Artifact -> Equipment.Artifact(
            id = id,
            name = name,
            description = description,
            passiveEffects = passiveEffects
        )

        EquipType.Other -> Equipment.Other(
            id,
            name,
            description
        )
    }
}

fun EquipmentWithStatus.toDomain(): Equipment {
    val baseEquipment = equipment.toDomain()
    baseEquipment.id = itemId.toString()
    if(baseEquipment is Equipment.Weapon)
        baseEquipment.isEquipped = isEquipped
    else if(baseEquipment is Equipment.Clothes)
        baseEquipment.isEquipped = isEquipped

    return baseEquipment
}

