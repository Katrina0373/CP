package com.mocalovak.cp.domain.model

enum class EquipType{
    Armor, Weapon, Potion, Artifact, Other
}

enum class BodyPart {
    Head, LeftHand, RightHand, Body, Legs, Foots, TwoHands
}

enum class ArmorWeight {
    Heavy, Light, Magic
}

sealed class Equipment {
    abstract var id: String
    abstract val name: String
    abstract val description: String
    open val tir: Int? = null

    data class Weapon(
        override var id: String,
        override val name: String,
        override val description: String,
        override val tir: Int?,
        val damage: String,
        val slot: List<BodyPart>,
        var isEquipped: Boolean = false,
        val passiveEffects: List<PassiveEffect>?,
        val activeEffect: String?, //активируется во время атаки
        val chance: Float? //шанс активации эффекта
    ) : Equipment()

    data class Clothes(
        override var id: String,
        override val name: String,
        override val description: String,
        override val tir: Int?,
        val slot: List<BodyPart>,
        var isEquipped: Boolean = false,
        val passiveEffects: List<PassiveEffect>?,
        val armorWeight: ArmorWeight?
    ) : Equipment()

    data class Potion(
        override var id: String,
        override val name: String,
        override val description: String,
        override val tir: Int?,
        val effect: String?,
    ) : Equipment()

    data class Artifact(
        override var id: String,
        override val name: String,
        override val description: String,
        val passiveEffects: List<PassiveEffect>?
    ) : Equipment()

    data class Other(
        override var id: String,
        override val name: String,
        override val description: String
    ): Equipment()
}
