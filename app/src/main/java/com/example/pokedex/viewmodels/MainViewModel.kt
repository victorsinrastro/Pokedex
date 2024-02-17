package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.network.models.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val loader = PokemonLoader()
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        _isLoading.value = true

        val call = loader.getPokemonList()
        call.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _pokemonList.value = response.body()?.pokemonList
                } else {
                    _errorMessage.value = "Error fetching Pokemon data"
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message ?: "Unknown error"
            }
        })
    }
}