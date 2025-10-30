package com.mocalovak.cp.domain.model

sealed class Race(name:String, passiveEffect: List<PassiveEffect>) {
    object Human: Race("Человек", listOf(PassiveEffect(
        "sword", 1f, "+1 к силе при использовании меча"),
        PassiveEffect("light magic", 2f, "+2 к магии света"),
        PassiveEffect("trading", 1f, "+1 к харизме при попытке торговаться")))
    //object HighElf: Race("Высший эльф", )
}