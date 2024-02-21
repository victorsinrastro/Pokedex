package com.example.pokedex.network.models

data class Pokemon(
    val name: String,
    val id: Int,
    val weight: Int,
    val height: Int,
    val abilities: List<Abilities>,
    val types: List<Types>,
    val imageUrl: String
)
