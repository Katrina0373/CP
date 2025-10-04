package com.mocalovak.cp.domain.model

data class Skill(
    val id:Int,
    val name: String,
    val description: String,
    val type: String,
    val accessLevel: Int,
    val check: String?,
    val recharge: String?,
    val damage: String?, //урон
    val actionTime: String?,
    //val passiveEffect: PassiveEffect,
    val mana:Int?,
)