package com.mocalovak.cp.utils

import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment

fun NameConverter(name: Any):String {

    return when (name) {
        EquipType.Armor -> "Броня"
        EquipType.Weapon -> "Оружие"
        EquipType.Potion -> "Зелья"
        EquipType.Artifact -> "Артефакты"
        EquipType.Other -> "Прочее"
        ArmorWeight.Magic -> "Магическое"
        ArmorWeight.Heavy -> "Тяжёлое"
        ArmorWeight.Light -> "Лёгкое"
        else -> "Прочее"
    }
}