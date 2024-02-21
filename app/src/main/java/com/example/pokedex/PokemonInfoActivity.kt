package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ActivityPokedexDetailBinding
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokemonInfoViewModel

class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityPokedexDetailBinding
    private lateinit var pokemonInfoViewModel: PokemonInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_pokedex_detail)
        setUpToolBar()
        val pokemonName = intent.getStringExtra(Constants.POKEMON_NAME) ?: ""
        pokemonInfoViewModel = ViewModelProvider(this)[PokemonInfoViewModel::class.java]
        pokemonInfoViewModel.fetchPokemonByName(pokemonName)
        observeViewModel()
    }

    private fun setUpToolBar() {
        val toolbar = detailBinding.toolbar as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() {
        pokemonInfoViewModel.pokemon.observe(this) { pokemon ->
            pokemon?.let {
                detailBinding.pokemon = it
                loadPokemonImage(it.sprites.frontShiny)
            }
        }
        pokemonInfoViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPokemonImage(imageUrl: String) {
        Glide.with(this@PokemonInfoActivity)
            .load(imageUrl)
            .into(detailBinding.imageViewPokemon)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}