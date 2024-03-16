package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.database.PokemonDatabase
import com.example.pokedex.databinding.ActivityPokemonInfoBinding
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokemonInfoViewModel
import com.example.pokedex.viewmodels.PokemonInfoViewModelFactory

class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityPokemonInfoBinding
    private lateinit var pokemonInfoViewModel: PokemonInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_info)
        val pokemonName = intent.getStringExtra(Constants.POKEMON_NAME) ?: ""
        setUpToolBar(pokemonName)
        val database = PokemonDatabase.getDatabase(this)
        pokemonInfoViewModel = ViewModelProvider(this,PokemonInfoViewModelFactory(database))[PokemonInfoViewModel::class.java]
        pokemonInfoViewModel.fetchPokemonByName(pokemonName)
        observeViewModel()
    }

    private fun setUpToolBar(pokemonName: String) {
        val toolbar = detailBinding.toolbar as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = pokemonName
        toolbar?.navigationIcon?.mutate()?.let { drawable ->
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.pokemon_yellow))
            toolbar.navigationIcon = drawable
        }
    }

    private fun observeViewModel() {
        pokemonInfoViewModel.pokemon.observe(this) { pokemon ->
            pokemon?.let {
                detailBinding.pokemon = it
                loadPokemonImage(it.imageUrl)
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