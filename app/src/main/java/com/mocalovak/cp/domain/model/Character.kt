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
    var maxHP:Int,
    var currentHP:Int,
    var gold:Int,
    var armorClass:Int,
    var speed:Int,
    val languages:List<String>?,
    val maxMana:Int,
    val currentMana:Int,
    var strength:Int,
    val dexterity:Int,
    val constitution:Int,
    val intelligence:Int,
    val magic:Int,
    val charisma:Int,
    val perception:Int
)