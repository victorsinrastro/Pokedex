package com.example.pokedex.network.models

import com.google.gson.annotations.SerializedName

open class PokemonListResponse {
    private var count: Int = 0
    private lateinit var next: String
    private lateinit var previous: String

    @SerializedName("results")
    private var pokemonList: MutableList<Pokemon> = mutableListOf()

    fun getCount(): Int {
        return count
    }

    fun getNext(): String {
        return next
    }

    fun getPrevious(): String {
        return previous
    }

    fun getPokemonList(): List<Pokemon> {
        return pokemonList
    }
}