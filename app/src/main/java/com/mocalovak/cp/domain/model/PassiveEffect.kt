package com.mocalovak.cp.domain.model

import kotlinx.coroutines.flow.Flow

data class PassiveEffect(val parameter:String, val bonus: Int, val description:String){
    override fun toString():String {
        return description
    }
}

fun List<PassiveEffect>?.takeString(): String {
    var result: String = ""
    this?.forEach {
        result += "$it, "
    }
    if(result.isNotEmpty()){
        result = result.dropLast(2)
    }
    return result
}

fun activatePassiveEffects(
    character: Character,
    equipment: List<Equipment>,
    skills: List<Skill>):Character {
    val actualPassives: MutableList<PassiveEffect> = emptyList<PassiveEffect>().toMutableList()
    equipment.forEach {
        if (it is Equipment.Clothes && it.isEquipped != null && it.passiveEffects != null)
            actualPassives += it.passiveEffects
        else if(it is Equipment.Weapon && it.isEquipped != null && it.passiveEffects != null)
            actualPassives += it.passiveEffects
        else if(it is Equipment.Artifact && it.passiveEffects != null)
            actualPassives += it.passiveEffects
    }

    skills.forEach {
        if(it.type == ActivePassive.Passive && it.passiveEffect != null){
            actualPassives += it.passiveEffect
        }
    }

    val statMap = mapOf(
        "maxHP" to Character::maxHP,
        "currentHP" to Character::currentHP,
        "armorClass" to Character::armorClass,
        "speed" to Character::speed,
        "maxMana" to Character::maxMana,
        "currentMana" to Character::currentMana,
        "strength" to Character::strength,
        "dexterity" to Character::dexterity,
        "constitution" to Character::constitution,
        "intelligence" to Character::intelligence,
        "magic" to Character::magic,
        "charisma" to Character::charisma,
        "perception" to Character::perception,
        "initiative" to Character::initiative
    )

    actualPassives.forEach { passive ->
        statMap[passive.parameter]?.let { prop ->
            prop.set(character, prop.get(character) + passive.bonus)
        }
    }

    return character
}
