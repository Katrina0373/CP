package com.mocalovak.cp.utils

import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.presentation.Character.EquipmentList

fun NameConverter(name: Any):String {

    return when (name) {
        EquipType.Armor -> "Броня"
        EquipType.Weapon -> "Оружие"
        EquipType.Potion -> "Зелья"
        EquipType.Artifact -> "Артефакты"
        EquipType.Other -> "Другое"
        ArmorWeight.Magic -> "Магическая"
        ArmorWeight.Heavy -> "Тяжёлая"
        ArmorWeight.Light -> "Лёгкая"
        is Equipment.Weapon -> "Оружие"
        is Equipment.Clother -> "Броня"
        is Equipment.Artifact -> "Артефакт"
        is Equipment.Potion -> "Зелье"
        is Equipment.Other -> "Другое"
        else -> "Прочее"
    }
}