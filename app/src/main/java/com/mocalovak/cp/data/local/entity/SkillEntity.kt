package com.mocalovak.cp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.PassiveEffect
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.model.Source
import org.intellij.lang.annotations.MagicConstant

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val description: String,
    val type: ActivePassive, //active_passive
    val useType: CombatMagic,
    val source: Source,
    val accessLevel: Int,
    val check: String?, //checking value magic, strength, and else
    val recharge: String?, //перезарядка
    val damage: String?, //урон
    val actionTime: String?,
    val passiveEffect: List<PassiveEffect>?,
    val mana :Int?,
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
    val skillId: Int
)

fun SkillEntity.toDomain(): Skill {
    return Skill(
        id,name, description, type, useType, source, accessLevel, check, recharge, damage, actionTime, passiveEffect, mana
    )
}