package com.mocalovak.cp.domain.model

enum class Modification(val title: String) {
    STRENGTH("Сила"),
    DEXTERITY("Ловкость"),
    CONSTITUTION("Телосложение"),
    INTELLIGENCE("Интеллект"),
    MAGIC("Магия"),
    CHARISMA("Харизма"),
    PERCEPTION("Восприятие")
}

data class Character(
    val id:Int,
    var name:String,
    val classification: String,
    val profession1:String?,
    val profession2: String?,
    val race: Race,
    val imagePath: String?,
    var level:Int,
    var maxHP:Int,
    var currentHP:Int,
    var gold:Int,
    var armorClass:Float,
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
) {
    fun hasUnregisterPassiveEffects(
        skills: List<Skill>?,
        equipment: List<Equipment>?
    ): List<PassiveEffect> {
        val fromEquipment: List<PassiveEffect> = equipment?.flatMap { equip ->
            when (equip) {
                is Equipment.Weapon -> equip.passiveEffects
                    ?.filterIsInstance<PassiveEffectWithCondition>()
                    ?: emptyList()
                is Equipment.Clothes -> equip.passiveEffects
                    ?.filterIsInstance<PassiveEffectWithCondition>()
                    ?: emptyList()
                else -> emptyList()
            }
        } ?: emptyList()

        val fromSkills = skills?.flatMap { it.passiveEffect ?: emptyList() } ?: emptyList()

        val fromRace = race.passiveEffect.filter {
            it is PassiveEffectWeapon || it is PassiveEffectWithCondition || it is PassiveEffectMagic
        }

        return fromEquipment + fromSkills + fromRace
    }
}