package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.pokemon.PokemonAdapter
import com.example.pokedex.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupObservers()
        setupRecyclerView()
    }

    private fun setupObservers() {
        viewModel.pokemonList.observe(this) { pokemonList ->
            binding.pokemonAdapter?.pokemonList = pokemonList
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarVisibility = isLoading
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = PokemonAdapter(emptyList())
        binding.pokemonAdapter = adapter
        binding.recyclerViewPokemonList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewPokemonList.layoutManager = layoutManager
    }
}