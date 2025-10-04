package com.mocalovak.cp.data.local.entity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated
import com.mocalovak.cp.domain.model.Character
import java.io.ByteArrayOutputStream

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    @Generated
    val id:String,
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
    var armorClass:Int,
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
    val perception:Int
    )

fun CharacterEntity.toDomain(): Character {
    return Character(
    id,
    name,
    classification,
    profession1,
    profession2,
    race,
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
    perception
    )
}


