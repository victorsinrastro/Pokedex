package com.example.pokedex.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"

class PokemonLoader : PokemonAPI {
    val pokemonApi: PokemonAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonAPI::class.java)
    }

    override suspend fun getPokemonList() = try {
        pokemonApi.getPokemonList()
    } catch (e: Exception) {
        throw NetworkException("Failed to fetch Pokémon list", e)
    }

    override suspend fun getPokemonInfo(name: String) = try {
        pokemonApi.getPokemonInfo(name)
    } catch (e: Exception) {
        throw NetworkException("Failed to fetch Pokémon info for $name", e)
    }
}

class NetworkException(message: String, cause: Throwable) : Exception(message, cause)

