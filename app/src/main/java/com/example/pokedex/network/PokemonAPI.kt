package com.example.pokedex.network

import com.example.pokedex.network.models.PokemonByIdResponse
import com.example.pokedex.network.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemonList(): Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: String): Call<PokemonByIdResponse>
}
