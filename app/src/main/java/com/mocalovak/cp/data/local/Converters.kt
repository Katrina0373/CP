package com.mocalovak.cp.data.local


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mocalovak.cp.domain.model.ArmorWeight
import com.mocalovak.cp.domain.model.BodyPart
import com.mocalovak.cp.domain.model.EquipType
import com.mocalovak.cp.domain.model.PassiveEffect

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String?>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String?> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromListPassiveEffect(effect: List<PassiveEffect>?): String {
        return gson.toJson(effect)
    }

    @TypeConverter
    fun toPassiveEffectList(data: String): List<PassiveEffect> {
        if (data.isEmpty()) return emptyList()
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
}
