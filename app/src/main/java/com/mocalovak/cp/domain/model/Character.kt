package com.mocalovak.cp.domain.model

import android.graphics.Bitmap

data class Character(
    val id:Int,
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
    var maxMana:Int,
    var currentMana:Int,
    var strength:Int,
    var dexterity:Int,
    var constitution:Int,
    var intelligence:Int,
    var magic:Int,
    var charisma:Int,
    var perception:Int,
    var initiative: Int
)