package com.example.pokedex.network.models

import com.example.pokedex.network.PokemonAPI
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PokemonAPITest {

    @Mock
    lateinit var mockCall: Call<PokemonListResponse>

    private lateinit var pokemonAPI: PokemonAPI

    @Test
    fun testGetPokemonList_Success() {
        // Mock a successful response from the API
        val mockResponse = Response.success(PokemonListResponse())
        `when`(mockCall.execute()).thenReturn(mockResponse)
        pokemonAPI = object : PokemonAPI {
            override fun getPokemonList(): Call<PokemonListResponse> {
                return mockCall
            }
        }
        val response = pokemonAPI.getPokemonList().execute()
        assert(response.isSuccessful)
    }
}