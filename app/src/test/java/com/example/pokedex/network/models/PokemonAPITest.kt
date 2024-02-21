package com.example.pokedex.network.models

import com.example.pokedex.network.PokemonAPI
import com.example.pokedex.network.PokemonLoader
import org.junit.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonAPITest {

    private val pokemonAPI: PokemonAPI = PokemonLoader().pokemonApi

    @Test
    fun testGetPokemonList_Success() {
        runBlocking {
            val pokemonListResponse = pokemonAPI.getPokemonList()
            assertNotNull(pokemonListResponse)
        }
    }

    @Test
    fun testGetPokemonInfo_Success() {
        runBlocking {
            val pokemonName = "charmander"
            val pokemonInfoResponse = pokemonAPI.getPokemonInfo(pokemonName)
            assertNotNull(pokemonInfoResponse)
        }
    }
}