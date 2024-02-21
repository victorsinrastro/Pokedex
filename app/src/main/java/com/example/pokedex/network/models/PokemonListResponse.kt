package com.example.pokedex.network.models

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(@SerializedName("results") val pokemonList: List<Pokemon>)