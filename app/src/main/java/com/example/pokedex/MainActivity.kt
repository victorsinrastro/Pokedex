package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.pokemon.PokemonAdapter
import com.example.pokedex.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar as Toolbar?)
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
        val pokemonAdapter = PokemonAdapter(emptyList())
        binding.apply {
            binding.pokemonAdapter = pokemonAdapter
            recyclerViewPokemonList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = pokemonAdapter
            }
        }
    }
}