package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.PokemonInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PokemonInfoViewModel : ViewModel() {
    private val loader = PokemonLoader()
    private val _pokemon = MutableLiveData<PokemonInfoResponse?>(null)
    val pokemon: LiveData<PokemonInfoResponse?> = _pokemon
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchPokemonByName(pokemonName: String) {
        viewModelScope.launch {
            try {
                val pokemonResponse = withContext(Dispatchers.IO) {
                    loader.getPokemonInfo(pokemonName)
                }
                _pokemon.value = pokemonResponse
            } catch (error: HttpException) {
                _errorMessage.value = "Error fetching Pokémon info: ${error.message()}"
            } catch (error: Exception) {
                _errorMessage.value = "Unknown error: ${error.message}"
            }
        }
    }
}
