package com.example.pokedex

import android.annotation.SuppressLint
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
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mainBinding.toolbar as Toolbar?)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
        setupRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        mainViewModel.pokemonList.observe(this) { pokemonList ->
            mainBinding.pokemonAdapter?.pokemonList = pokemonList
            mainBinding.pokemonAdapter?.notifyDataSetChanged()
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            mainBinding.progressBarVisibility = isLoading
        }

        mainViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val pokemonAdapter = PokemonAdapter(emptyList())
        mainBinding.apply {
            mainBinding.pokemonAdapter = pokemonAdapter
            recyclerViewPokemons.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = pokemonAdapter
            }
        }
    }
}