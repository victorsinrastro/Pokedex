package com.example.pokedex.network

import com.example.pokedex.network.models.PokemonInfoResponse
import com.example.pokedex.network.models.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): PokemonInfoResponse
}
