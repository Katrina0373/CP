package com.mocalovak.cp.utils

import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.Equipment
import com.mocalovak.cp.domain.model.Source

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
        is Equipment.Clothes -> "Броня"
        is Equipment.Artifact -> "Артефакт"
        is Equipment.Potion -> "Зелье"
        is Equipment.Other -> "Другое"
        ActivePassive.Active -> "Активный"
        ActivePassive.Passive -> "Пассивный"
        CombatMagic.Combat -> "Боевой"
        CombatMagic.Magic -> "Магический"
        Source.Race -> "Расовый"
        Source.Common -> "Общий"
        Source.Profession -> "Классовый"
        else -> "Прочее"
    }
}