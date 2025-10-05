package com.mocalovak.cp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.PassiveEffect
import org.jetbrains.annotations.NotNull
import javax.annotation.processing.Generated

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey
    @Generated
    val id:String,
    val name:String,
    val type: EquipType,
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
    @Embedded val equipment: EquipmentEntity,
    val isEquipped: Boolean
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
            chance = chance
        )
        EquipType.Armor -> Equipment.Clother(
            id = id,
            name = name,
            description = description,
            slot = slot.map { it!! },
            passiveEffects = passiveEffects,
            //isEquipped = isEquipped,
            armorWeight = weight
        )
        EquipType.Potion -> Equipment.Potion(
            id = id,
            name = name,
            description = description,
            effect = effect ?: ""
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

fun EquipmentWithStatus.toDomain():Equipment {
    val baseEquipment = equipment.toDomain()

    if(baseEquipment is Equipment.Weapon)
        baseEquipment.isEquipped = isEquipped
    else if(baseEquipment is Equipment.Clother)
        baseEquipment.isEquipped = isEquipped

    return baseEquipment
}

