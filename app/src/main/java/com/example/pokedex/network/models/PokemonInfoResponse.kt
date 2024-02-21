package com.example.pokedex.network.models

import com.google.gson.annotations.SerializedName

data class PokemonInfoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("abilities") val abilities: List<Abilities>,
    @SerializedName("types") val types: List<Types>,
    @SerializedName("sprites") val sprites: Sprites
)

data class Abilities(
    @SerializedName("ability") val ability: Ability
)

data class Ability(
    @SerializedName("name") val name: String
)

data class Types(
    @SerializedName("type") val type: Type
)

data class Type(
    @SerializedName("name") val name: String
)

data class Sprites(
    @SerializedName("front_shiny") val frontShiny: String
)
