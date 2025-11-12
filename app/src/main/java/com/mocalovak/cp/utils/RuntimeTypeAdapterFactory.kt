package com.mocalovak.cp.utils


import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * Полная реализация RuntimeTypeAdapterFactory для Kotlin.
 * Позволяет сериализовать и десериализовать иерархии классов с полиморфизмом.
 */
class RuntimeTypeAdapterFactory<T>(
    private val baseType: Class<T>,
    private val typeFieldName: String,
    private val maintainType: Boolean = false
) : TypeAdapterFactory {

    private val labelToSubtype = mutableMapOf<String, Class<out T>>()
    private val subtypeToLabel = mutableMapOf<Class<out T>, String>()

    fun registerSubtype(type: Class<out T>, label: String = type.simpleName): RuntimeTypeAdapterFactory<T> {
        labelToSubtype[label] = type
        subtypeToLabel[type] = label
        return this
    }

    override fun <R : Any> create(gson: Gson, type: TypeToken<R>): TypeAdapter<R>? {
        if (!baseType.isAssignableFrom(type.rawType)) return null

        val labelToDelegate = labelToSubtype.mapValues {
            gson.getDelegateAdapter(this, TypeToken.get(it.value))
        }

        val subtypeToDelegate = subtypeToLabel.mapValues {
            gson.getDelegateAdapter(this, TypeToken.get(it.key))
        }

        return object : TypeAdapter<R>() {
            override fun write(out: JsonWriter, value: R) {
                val srcType = value.javaClass
                val label = subtypeToLabel[srcType as Class<out T>]
                    ?: throw IllegalArgumentException("Unknown subtype: $srcType")
                val delegate = subtypeToDelegate[srcType] as TypeAdapter<R>
                val json = delegate.toJsonTree(value).asJsonObject

                if (maintainType) {
                    out.jsonValue(json.toString())
                    return
                }

                val clone = JsonObject()
                clone.add(typeFieldName, gson.toJsonTree(label))
                json.entrySet().forEach { (k, v) -> clone.add(k, v) }
                out.jsonValue(clone.toString())
            }

            override fun read(reader: JsonReader): R {
                val jsonElement = gson.fromJson<JsonElement>(reader, JsonElement::class.java)
                val jsonObject = jsonElement.asJsonObject
                val labelJson = jsonObject.remove(typeFieldName)
                    ?: throw IllegalArgumentException("Missing type field: $typeFieldName")
                val label = labelJson.asString
                val delegate = labelToDelegate[label]
                    ?: throw IllegalArgumentException("Unknown label: $label")
                return delegate.fromJsonTree(jsonObject) as R
            }
        }
    }
}


