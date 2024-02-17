package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.PokemonByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexDetailViewModel : ViewModel() {
    private val loader = PokemonLoader()
    private val _pokemon = MutableLiveData<PokemonByIdResponse?>(null)
    val pokemon: LiveData<PokemonByIdResponse?> = _pokemon
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchPokemonById(pokemonId: String) {
        val call = loader.getPokemonById(pokemonId)

        call.enqueue(object : Callback<PokemonByIdResponse> {
            override fun onResponse(
                call: Call<PokemonByIdResponse>,
                response: Response<PokemonByIdResponse>
            ) {
                val pokemon: PokemonByIdResponse? = response.body()
                pokemon?.let {
                    _pokemon.value = it
                }

            }

            override fun onFailure(call: Call<PokemonByIdResponse>, t: Throwable) {
                _errorMessage.value = t.message ?: "Unknown error"
            }
        })
    }
}
