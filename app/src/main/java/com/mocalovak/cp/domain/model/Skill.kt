package com.mocalovak.cp.domain.model

enum class ActivePassive { Active, Passive}

enum class CombatMagic { Combat, Magic }

enum class Source { Race, Common, Profession}

enum class MagicType { Air, Light, Dark, Fire, Water}

open class Skill(
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

class MagicSkill(
    id: Int, name: String, description: String, type: ActivePassive, useType: CombatMagic,
    source: Source, accessLevel: Int, check: String?, recharge: String?, damage: String?,
    actionTime: String?, passiveEffect: List<PassiveEffect>?, mana: Int?,
    val magicType: MagicType

): Skill(id, name, description, type, useType, source, accessLevel, check, recharge, damage,
    actionTime, passiveEffect, mana
) {
}