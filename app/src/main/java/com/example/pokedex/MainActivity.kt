package com.example.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.network.models.PokemonListResponse
import com.example.pokedex.pokemon.PokemonAdapter
import com.example.pokedex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val loader = PokemonLoader()
    private val call = loader.getPokemonList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBarVisibility = true

        call.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                binding.progressBarVisibility = false
                val pokemonList: List<Pokemon> = response.body()!!.pokemonList
                val adapter = PokemonAdapter(pokemonList)
                binding.pokemonAdapter = adapter
                binding.recyclerViewPokemonList.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(this@MainActivity)
                binding.recyclerViewPokemonList.layoutManager = layoutManager
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                binding.progressBarVisibility = false
                Log.e(Constants.DEBUG_POKEMON, t.message ?: "Unknown error")
                Toast.makeText(
                    this@MainActivity,
                    "Failed to fetch Pokemon list. Please check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}