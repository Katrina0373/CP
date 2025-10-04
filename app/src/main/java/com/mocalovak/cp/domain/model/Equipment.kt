package com.mocalovak.cp.domain.model

import android.media.audiofx.DynamicsProcessing.Eq
import com.mocalovak.cp.presentation.HomePage.HomePageUiState

enum class EquipType{
    Armor, Weapon, Potion, Artifact, Other
}

enum class BodyPart {
    Head, LeftHand, RightHand, Body, Legs, Foots
}

enum class ArmorWeight {
    Heavy, Light, Magic
}

sealed class Equipment {
    abstract val id: String
    abstract val name: String
    abstract val description: String

    data class Weapon(
        override val id: String,
        override val name: String,
        override val description: String,
        val damage: String,
        val slot: List<BodyPart>,
        val isEquipped: Boolean = false,
        val passiveEffects: List<PassiveEffect>?,
        val activeEffect: String?, //активируется во время атаки
        val chance: Float? //шанс активации эффекта
    ) : Equipment()

    data class Clother(
        override val id: String,
        override val name: String,
        override val description: String,
        val slot: List<BodyPart>,
        val isEquipped: Boolean = false,
        val passiveEffects: List<PassiveEffect>?,
        val armorWeight: ArmorWeight?
    ) : Equipment()

    data class Potion(
        override val id: String,
        override val name: String,
        override val description: String,
        val effect: String
    ) : Equipment()

    data class Artifact(
        override val id: String,
        override val name: String,
        override val description: String,
        val passiveEffects: List<PassiveEffect>?
    ) : Equipment()

    data class Other(
        override val id:String,
        override val name: String,
        override val description: String
    ): Equipment()
}
