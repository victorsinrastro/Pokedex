package com.example.pokedex.network.models

open class PokemonListResponse {
    private  var count: Int = 0
    private lateinit var next: String
    private lateinit var previous: String
    private var pokemonList: MutableList<Pokemon> = mutableListOf()

    data class Pokemon(val name: String, val url: String)

}