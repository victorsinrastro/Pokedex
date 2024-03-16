package com.example.pokedex.database

import androidx.room.TypeConverter
import com.example.pokedex.network.models.Abilities
import com.example.pokedex.network.models.Types
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun abilitiesListToJson(abilities: List<Abilities>): String? {
        return abilities.let { gson.toJson(it) }
    }

    @TypeConverter
    fun jsonToAbilitiesList(json: String): List<Abilities>? {
        return json.let {
            gson.fromJson(json, object : TypeToken<List<Abilities>>() {}.type)
        }
    }

    @TypeConverter
    fun typesListToJson(types: List<Types>): String? {
        return types.let { gson.toJson(it) }
    }

    @TypeConverter
    fun jsonToTypesList(json: String): List<Types>? {
        return json.let {
            gson.fromJson(json, object : TypeToken<List<Types>>() {}.type)
        }
    }
}