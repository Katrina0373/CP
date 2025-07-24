package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mocalovak.cp.domain.model.Skill

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey
    val name: String,
    val description: String,
    val type: String,
    val source: String,
    val accessLevel: Int,
    val check: Int, //значение проверки?????
    val recharge: String, //перезарядка
    //val damage: List<Int>, //урон
    val actionTime: String,
    //val passiveEffect: PassiveEffect,
    val mana:Int,
)

fun SkillEntity.toDomain(): Skill {
    return Skill(
        name, description, type, source, accessLevel, check, recharge, actionTime, mana
    )
}