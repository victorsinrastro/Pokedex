package com.example.pokedex.network

import com.example.pokedex.network.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemonList(): Call<PokemonListResponse>
}