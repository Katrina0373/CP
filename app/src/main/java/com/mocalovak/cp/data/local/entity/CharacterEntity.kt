package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.model.Race

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name:String,
    val classification: String,
    val profession1:String?,
    val profession2:String?,
    val race:String,
    var imagePath: String?,
    var level:Int,
    var maxHP:Int,
    var currentHP:Int,
    var gold:Int,
    var armorClass:Float,
    var speed:Int,
    val languages:List<String>?,
    val maxMana:Int,
    val currentMana:Int,
    val strength:Int,
    val dexterity:Int,
    val constitution:Int,
    val intelligence:Int,
    val magic:Int,
    val charisma:Int,
    val perception:Int,
    val initiative: Int
    )

fun CharacterEntity.toDomain(): Character {
    return Character(
    id,
    name,
    classification,
    profession1,
    profession2,
    race = Race::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .first { it.name == race },
    imagePath,
    level,
    maxHP,
    currentHP,
    gold,
    armorClass,
    speed,
    languages,
    maxMana,
    currentMana,
    strength,
    dexterity,
    constitution,
    intelligence,
    magic,
    charisma,
    perception,
        dexterity + initiative
    )
}

fun Character.toEntity(withID:Boolean): CharacterEntity {
    return CharacterEntity(
        id = if(withID) id else 1,
        name = name,
        classification = classification,
        profession1 = profession1,
        profession2 = profession2,
        race = race.name,
        imagePath = imagePath,
        level = level,
        maxHP = maxHP,
        currentHP = currentHP,
        gold = gold,
        armorClass = armorClass,
        speed = speed,
        languages = languages,
        maxMana = maxMana,
        currentMana = currentMana,
        strength = strength,
        dexterity = dexterity,
        constitution = constitution,
        intelligence = intelligence,
        magic = magic,
        charisma = charisma,
        perception = perception,
        initiative = initiative - dexterity
    )
}


