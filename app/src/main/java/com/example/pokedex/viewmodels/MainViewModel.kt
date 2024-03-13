package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

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
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val pokemonListResponse = withContext(Dispatchers.IO) {
                    loader.getPokemonList()
                }
                val updatedPokemonList = pokemonListResponse.pokemonList.map { pokemon ->
                    async(Dispatchers.IO) {
                        val pokemonInfo = loader.getPokemonInfo(pokemon.name)
                        pokemon.imageUrl = pokemonInfo.sprites.other.officialArtwork.frontShiny
                        pokemon.id = pokemonInfo.id
                        pokemon
                    }
                }.awaitAll()
                _pokemonList.value = updatedPokemonList
            } catch (e: HttpException) {
                _errorMessage.value = "Error fetching Pokemon List: ${e.message()}"
            } catch (e: IOException) {
                _errorMessage.value = "Network error. Please check your connection."
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong. Please try again later."
            } finally {
                _isLoading.value = false
            }
        }
    }
}