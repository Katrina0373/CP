package com.mocalovak.cp.domain.model

import com.mocalovak.cp.presentation.HomePage.HomePageUiState

enum class EquipType{
    Armor, Weapon
}

enum class BodyPart {
    Head, LeftHand, RightHand, Body, Legs, Foots
}

sealed class Equipment {
    data class Weapon(val id: String,
                      val name:String,
                      val damage:String,
                      val slot: BodyPart,
        val passiveEffect: PassiveAffect,
        )
    data class Clother(
        val id: String,
        val name: String,
        val slot: BodyPart,
        val passiveEffect: PassiveEffect
    )
}