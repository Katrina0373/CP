package com.mocalovak.cp.domain.model

import android.graphics.Bitmap

data class Character(
    val id:String,
    val name:String,
    val classification: String,
    val profession1:String?,
    val profession2: String?,
    val race:String,
    val imagePath: String?,
    var level:Int,
    val maxHP:Int,
    var currentHP:Int,
    var gold:Int,
    val armorClass:Int,
    val speed:Int,
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