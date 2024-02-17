package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ActivityPokedexDetailBinding
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokedexDetailViewModel

class PokedexDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokedexDetailBinding
    private lateinit var viewModel: PokedexDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokedex_detail)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val pokemonId = intent.getStringExtra(Constants.POKEMON_ID) ?: ""
        viewModel = ViewModelProvider(this)[PokedexDetailViewModel::class.java]
        viewModel.fetchPokemonById(pokemonId)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.pokemon.observe(this) {pokemon ->
            pokemon?.let {
                binding.pokemon = it
                loadPokemonImage(it.sprites.frontShiny)
            }
        }
        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPokemonImage(imageUrl: String) {
        Glide.with(this@PokedexDetailActivity)
            .load(imageUrl)
            .into(binding.imageViewPokemon)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}