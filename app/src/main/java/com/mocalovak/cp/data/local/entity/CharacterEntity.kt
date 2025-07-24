package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated
import com.mocalovak.cp.domain.model.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    @Generated
    val id:String,
    val name:String,
    val classification: String,
    val profession1:String,
    val profession2:String,
    val race:String,
    var level:Int,
    var maxHP:Int,
    var currentHP:Int,
    var gold:Int,
    var armorClass:Int,
    var speed:Int,
    //val languages:List<String>?,
    //var equipment:List<EquipmentEntity>?,
    val maxMana:Int,
    val currentMana:Int,
    val strength:Int,
    val dexterity:Int,
    val constitution:Int,
    val intelligence:Int,
    val wisdom:Int,
    val charisma:Int,
    //var passiveEffects: List<PassiveEffect>?,
    )

fun CharacterEntity.toDomain(): Character {
    return Character(
    id,
    name,
    classification,
    profession1,
    profession2,
    race,
    level,
    maxHP,
    currentHP,
    gold,
    armorClass,
    speed,
    //val languages:List<String>?,
    //var equipment:List<EquipmentEntity>?,
    maxMana,
    currentMana,
    strength,
    dexterity,
    constitution,
    intelligence,
    wisdom,
    charisma,
    )
}