package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.mocalovak.cp.domain.model.Skill

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey(autoGenerate = true)
    val id:String,
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

@Entity(
    tableName = "skill_character_cross_ref",
    primaryKeys = ["characterId", "skillId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = SkillEntity::class,
        parentColumns = ["id"],
        childColumns = ["skillId"],
        onDelete = ForeignKey.CASCADE)

    ],
    indices = [Index("characterId"), Index("skillId")]
)
data class SkillCharacterCrossRef(
    val characterId:String,
    val skillId:String
)

fun SkillEntity.toDomain(): Skill {
    return Skill(
        name, description, type, source, accessLevel, check, recharge, actionTime, mana
    )
}