package com.mocalovak.cp.domain.model

enum class ActivePassive { Active, Passive}

enum class CombatMagic { Combat, Magic }

enum class Source { Race, Common, Profession}

data class Skill(
    val id: Int,
    val name: String,
    val description: String,
    val type: ActivePassive,
    val useType: CombatMagic,
    val source: Source,
    val accessLevel: Int,
    val check: String?,
    val recharge: String?,
    val damage: String?, //урон
    val actionTime: String?,
    val passiveEffect: List<PassiveEffect>?,
    val mana:Int?,
)