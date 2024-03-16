package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.database.PokemonDatabase
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.network.models.PokemonInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PokemonInfoViewModel(
    private val database: PokemonDatabase
) : ViewModel() {
    private val _pokemon = MutableLiveData<Pokemon?>(null)
    val pokemon: LiveData<Pokemon?> = _pokemon

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchPokemonByName(pokemonName: String) {
        viewModelScope.launch {
            try {
                val cachedPokemon = getCachedPokemon(pokemonName)
                _pokemon.value = cachedPokemon
            } catch (error: HttpException) {
                _errorMessage.value = "Error fetching Pokémon info: ${error.message()}"
            } catch (error: Exception) {
                _errorMessage.value = "Unknown error: ${error.message}"
            }
        }
    }

    private suspend fun getCachedPokemon(pokemonName: String): Pokemon? {
        return withContext(Dispatchers.IO) {
            database.pokemonDao().getPokemonByName(pokemonName)
        }
    }
}
