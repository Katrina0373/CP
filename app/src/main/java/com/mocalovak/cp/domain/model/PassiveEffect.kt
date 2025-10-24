package com.mocalovak.cp.domain.model

import kotlinx.coroutines.flow.Flow
import kotlin.math.floor

data class PassiveEffect(val parameter:String, val bonus: Float, val description:String){
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
    skills: List<Skill>
): Character {
    // Собираем все пассивки
    val actualPassives:List<PassiveEffect>? = buildList {
        equipment.forEach {
            when (it) {
                is Equipment.Clothes -> if (it.isEquipped != null) it.passiveEffects?.let(::addAll)
                is Equipment.Weapon -> if (it.isEquipped != null) it.passiveEffects?.let(::addAll)
                is Equipment.Artifact -> if(it.isSet) it.passiveEffects?.let(::addAll)
                else -> {}
            }
        }
        skills.forEach {
            if (it.type == ActivePassive.Passive) it.passiveEffect?.let(::addAll)
        }
    }

    println("hi")
    // Создаём новый объект Character, применяя все бонусы последовательно
    if (actualPassives != null) {
        return actualPassives.fold(character) { acc, passive ->
            when (passive.parameter) {
                "maxHP" -> acc.copy(maxHP = acc.maxHP + floor(passive.bonus).toInt())
                "currentHP" -> acc.copy(currentHP = acc.currentHP + floor(passive.bonus).toInt())
                "armorClass" -> acc.copy(armorClass = acc.armorClass + passive.bonus)
                "speed" -> acc.copy(speed = acc.speed + floor(passive.bonus).toInt())
                "maxMana" -> acc.copy(maxMana = acc.maxMana + floor(passive.bonus).toInt())
                "currentMana" -> acc.copy(currentMana = acc.currentMana + floor(passive.bonus).toInt())
                "strength" -> acc.copy(strength = acc.strength + floor(passive.bonus).toInt())
                "dexterity" -> acc.copy(dexterity = acc.dexterity + floor(passive.bonus).toInt())
                "constitution" -> acc.copy(constitution = acc.constitution + floor(passive.bonus).toInt())
                "intelligence" -> acc.copy(intelligence = acc.intelligence + floor(passive.bonus).toInt())
                "magic" -> acc.copy(magic = acc.magic + floor(passive.bonus).toInt())
                "charisma" -> acc.copy(charisma = acc.charisma + floor(passive.bonus).toInt())
                "perception" -> acc.copy(perception = acc.perception + floor(passive.bonus).toInt())
                "initiative" -> acc.copy(initiative = acc.initiative + floor(passive.bonus).toInt())
                else -> acc
            }
        }
    }
    else return character
}

