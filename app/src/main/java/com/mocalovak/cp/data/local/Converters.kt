package com.mocalovak.cp.data.local


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.domain.model.ActivePassive
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.CombatMagic
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.PassiveEffect
import com.mocalovak.cp.domain.model.PassiveEffectWithCondition
import com.mocalovak.cp.domain.model.Source
import com.mocalovak.cp.utils.RuntimeTypeAdapterFactory

val passiveEffectAdapterFactory: RuntimeTypeAdapterFactory<PassiveEffect> =
    RuntimeTypeAdapterFactory(
        baseType = PassiveEffect::class.java,
        typeFieldName = "type") // ключевое поле в JSON
        .registerSubtype(PassiveEffect::class.java, "base")
        .registerSubtype(PassiveEffectWithCondition::class.java, "with_condition")

val gson: Gson = GsonBuilder()
    .registerTypeAdapterFactory(passiveEffectAdapterFactory)
    .create()

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListPassiveEffect(effect: List<PassiveEffect>?): String {
        return gson.toJson(effect)
    }

    @TypeConverter
    fun toPassiveEffectList(data: String?): List<PassiveEffect>? {
        if (data.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<PassiveEffect>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromBodyPartList(list: List<BodyPart?>): String {
        return list
            .joinToString(",") { it?.name ?: "null" }
    }

    @TypeConverter
    fun toBodyPartList(data: String): List<BodyPart?> {
        if (data.isEmpty()) return emptyList()
        return data.split(",").map {
            if (it == "null") null else BodyPart.valueOf(it)
        }
    }

    @TypeConverter
    fun fromEquipType(value:EquipType) : String = value.name

    @TypeConverter
    fun toEquipType(value:String) : EquipType = EquipType.valueOf(value)


    @TypeConverter
    fun fromArmorWeight(value:ArmorWeight) : String = value.name

    @TypeConverter
    fun toArmorWeight(value:String) : ArmorWeight = ArmorWeight.valueOf(value)

    @TypeConverter
    fun fromActivePassive(value: ActivePassive) : String = value.name

    @TypeConverter
    fun toActivePassive(value:String) : ActivePassive = ActivePassive.valueOf(value)

    @TypeConverter
    fun fromCombatMagic(value: CombatMagic) : String = value.name

    @TypeConverter
    fun toCombatMagic(value:String) : CombatMagic = CombatMagic.valueOf(value)

    @TypeConverter
    fun fromSource(value: Source) : String = value.name

    @TypeConverter
    fun toSource(value:String) : Source = Source.valueOf(value)
}
