package com.example.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.network.models.PokemonInfoResponse

@Dao
interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<Pokemon>)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemon(): List<Pokemon>

    @Query("SELECT * FROM pokemon_table WHERE name = :pokemonName LIMIT 1")
    suspend fun getPokemonByName(pokemonName: String): Pokemon?

}