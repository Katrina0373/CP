package com.mocalovak.cp.domain

import com.mocalovak.cp.domain.model.Character


interface SkillEffect {
    fun apply(character: Character): String
}

class FireballEffect : SkillEffect {
    override fun apply(character: Character): String {
        return "D20 + ${character.intelligence} | 2D8 + ${character.intelligence}"
    }
}

class HealEffect: SkillEffect{
    override fun apply(character: Character): String {
        return "D4 + ${character.constitution}"
    }
}

val skillEffects = mapOf(
    "fireball" to FireballEffect(),
    "heal" to HealEffect()
)
