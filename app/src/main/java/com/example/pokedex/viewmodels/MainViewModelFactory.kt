package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.database.PokemonDatabase
import com.example.pokedex.network.PokemonLoader

/**
 * Factory class for creating [MainViewModel].
 * @property loader The PokemonLoader instance.
 * @property database The PokemonDatabase instance.
 */
class MainViewModelFactory(
    private val loader: PokemonLoader,
    private val database: PokemonDatabase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified [ViewModel].
     * @param modelClass The class of the ViewModel to create.
     * @return A newly created instance of the ViewModel.
     * @throws IllegalArgumentException If the specified ViewModel class is unknown.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            val constructor = modelClass.getConstructor(PokemonLoader::class.java, PokemonDatabase::class.java)
            constructor.newInstance(loader, database)
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}