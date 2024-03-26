package com.example.pokedex.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.database.PokemonDatabase
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val loader: PokemonLoader, private val database: PokemonDatabase) :
    ViewModel() {
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
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cachedPokemon = getCachedPokemon()
                if (cachedPokemon.isNotEmpty()) {
                    _pokemonList.value = cachedPokemon
                } else {
                    val updatedPokemonList = fetchPokemonFromNetwork()
                    _pokemonList.value = updatedPokemonList
                    insertPokemonToDatabase(updatedPokemonList)
                }
            } catch (e: Exception) {
                handleNetworkError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun getCachedPokemon(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            database.pokemonDao().getAllPokemon()
        }
    }

    private suspend fun fetchPokemonFromNetwork(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val pokemonListResponse = loader.getPokemonList()
            pokemonListResponse.pokemonList.map { pokemon ->
                val pokemonInfo = loader.getPokemonInfo(pokemon.name)
                pokemon.apply {
                    imageUrl = pokemonInfo.sprites.other.officialArtwork.frontShiny
                    id = pokemonInfo.id
                    abilities = pokemonInfo.abilities
                    types = pokemonInfo.types
                    weight = pokemonInfo.weight
                    height = pokemonInfo.height
                    stats = pokemonInfo.stats
                }
            }
        }
    }

    private suspend fun insertPokemonToDatabase(pokemonList: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            database.pokemonDao().insertAll(pokemonList)
        }
    }

    private fun handleNetworkError(e: Exception) {
        when (e) {
            is HttpException -> _errorMessage.value = "Error fetching Pokémon list: ${e.message()}"
            is IOException -> _errorMessage.value = "Network error. Please check your connection."
            else -> {
                _errorMessage.value =
                    "Something went wrong. Please try again later. ${e.localizedMessage}"
                Log.e("MainViewModel", "fetchPokemonList: ${e.message}", e)
            }
        }
    }
}