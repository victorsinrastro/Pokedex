package com.example.pokedex.network

import com.example.pokedex.network.models.PokemonByIdResponse
import com.example.pokedex.network.models.PokemonListResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL_BASE = "https://pokeapi.co/api/v2/"

class PokemonLoader : PokemonAPI {
    private val pokemonApi: PokemonAPI


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokemonApi = retrofit.create(PokemonAPI::class.java)
    }

    override fun getPokemonList(): Call<PokemonListResponse> {
        return pokemonApi.getPokemonList()
    }

    override fun getPokemonById(id: String): Call<PokemonByIdResponse> {
        return pokemonApi.getPokemonById(id)
    }
}