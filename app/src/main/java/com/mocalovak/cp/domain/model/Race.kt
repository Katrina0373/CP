package com.mocalovak.cp.domain.model

sealed class Race(val name:String, val passiveEffect: List<PassiveEffect>) {
    object Human: Race("Человек", listOf(
        PassiveEffectWeapon("strength", 1f, "+1 к силе при использовании меча", {it.name.contains("меч")}),
        PassiveEffectMagic("magic", 2f, "+2 к магии света", {it.magicType == MagicType.light}),
        PassiveEffectWithCondition("charisma", 1f, "+1 к харизме", "при попытке торговаться")))
    object HighElf: Race("Высший эльф", listOf<PassiveEffect>(
        PassiveEffectMagic("magic", 1f, "+1 к атакам магией воздуха", {it.magicType == MagicType.air}),
        PassiveEffectWithCondition("magic", 1f, "+1 к магии", "днём и на открытых пространствах"),
        PassiveEffectWithCondition("perception", 1f, "+1 к восприятию", "днём и на открытых пространствах"),
        PassiveEffectWithCondition("perception", -1f, "-1 к восприятию", "ночью"),
        PassiveEffectWithCondition("magic", -1f, "-1 к магии", "ночью"),
    ))
    object WoodElf: Race("Лесной эльф", listOf(
        PassiveEffectMagic("magic", 2f, "+2 к заклинаниям лечения", {it.name.contains("лечение",ignoreCase = true)}),
        PassiveEffectWeapon("perception", 1f, "+1 к урону от дальнобойных атак", {it.distance > 3}),
        PassiveEffectWithCondition("dexterity", 1f, "+1 ловкость", "при попытке взлома"),
    ))
    object DarkElf: Race("Тёмный эльф", listOf(
        PassiveEffectWithCondition("perception", 1f, "+1 к восприятию", "ночью и в закрытых пространствах"),
        PassiveEffectMagic("magic", 1f, "+1 к тёмной магии") { it.magicType == MagicType.dark },
        PassiveEffectWithCondition("critical attack", 0.1f, "шанс на критическую атаку +10%", ""),
        PassiveEffectWithCondition("dexterity", 1f, "+1 к ловкости", "при скрытой атаке"),
        PassiveEffectWithCondition("dexterity", 1f, "+1 к ловкости", "при попытке взлома")
    ))
    object Orc: Race ("Орк", listOf(
        PassiveEffectWithCondition("constitution", 3f, "+3 к телосложению", "при спасбросках от яда"),
        PassiveEffectWeapon("strength", 1f, "+1 к силе при использовании двуручного оружия") { it.isEquipped == BodyPart.TwoHands},
        PassiveEffectWithCondition("charisma", 2f, "+2 к харизме", "при запугивании"),
        PassiveEffectWithCondition("charisma", 0f, "отрицательный параметр харизмы превращается в положительный", "при запугивании"),
    ))
    object Dwarf: Race("Дварф", listOf(
        PassiveEffectWithCondition("perception", 1f, "+1 к воприятию", "в темноте"),
        PassiveEffectWeapon("intelligence", 1f, "+1 к интеллекту при использовании огнестрельного оружия", { it.name.contains("пистолет", ignoreCase = true) || it.name.contains("ружьё", ignoreCase = true) }),
        PassiveEffectWeapon("strength", 1f, "+1 к силе при использовании молота",  {it.name.contains("молот", ignoreCase = true)}),
        PassiveEffectWeapon("stunning", 0.1f, "+10% к шансу оглушения при использовании молота") {it.name.contains("молот", ignoreCase = true)}
    ))
    data object Tabaksi: Race("Табакси", listOf(
        PassiveEffectWithCondition("dexterity", 1f, "+1 к ловкости", "при проверках скрытности"),
        PassiveEffectWithCondition("dexterity", 1f, "+1 к ловкости", "при попытках взлома"),
        PassiveEffectWithCondition("dexterity", 1f, "+1 к ловкости", "при скрытой атаке"),
        PassiveEffectWithCondition("critical attack", 0.1f, "+10% к шансу критической атаки с оружием", "") //Потом исправить тип!
    ))
    object Lizardman: Race("Людоящер", listOf(
        PassiveEffectWithCondition("constitution", 3f, "+3 к спасброску телосложения", "при спасбросоках от яда"),
        PassiveEffectWeapon("strength", 1f, "+1 к попаданию и урону при использовании щита") {it.name.contains("Щит", ignoreCase = true)},
        PassiveEffectMagic("magic", 1f, "+1 к магии при использовании заклинаний воды") {it.magicType == MagicType.water}
    ))
}