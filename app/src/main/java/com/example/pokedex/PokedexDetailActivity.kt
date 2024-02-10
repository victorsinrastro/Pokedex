package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ActivityPokedexDetailBinding
import com.example.pokedex.network.PokemonLoader
import com.example.pokedex.network.models.PokemonByIdResponse
import com.example.pokedex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokedexDetailBinding
    private val loader = PokemonLoader()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokedex_detail)
        val pokemonId = intent.getStringExtra(Constants.POKEMON_ID) ?: ""
        val call = loader.getPokemonById(pokemonId)

        call.enqueue(object : Callback<PokemonByIdResponse> {
            override fun onResponse(
                call: Call<PokemonByIdResponse>,
                response: Response<PokemonByIdResponse>
            ) {
                val pokemon: PokemonByIdResponse? = response.body()
                pokemon?.let {
                    binding.pokemon = it

                    it.sprites.frontShiny.let { imageUrl ->
                        Glide.with(this@PokedexDetailActivity)
                            .load(imageUrl)
                            .into(binding.imageViewPokemon)
                    }
                }

            }

            override fun onFailure(call: Call<PokemonByIdResponse>, t: Throwable) {
                Toast.makeText(
                    this@PokedexDetailActivity,
                    "Failed to fetch Pokemon list. Please check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}