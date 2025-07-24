package com.mocalovak.cp.domain.model

data class Skill(
    val name: String,
    val description: String,
    val type: String,
    val source: String,
    val accessLevel: Int,
    val check: Int, //значение проверки?????
    val recharge: String, //перезарядка
    //val damage: List<Int>, //урон
    val actionTime: String,
    //val passiveEffect: PassiveEffect,
    val mana:Int,
)