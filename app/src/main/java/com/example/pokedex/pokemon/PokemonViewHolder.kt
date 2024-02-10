package com.example.pokedex.pokemon

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokedex.PokedexDetailActivity
import com.example.pokedex.databinding.ItemPokemonListBinding
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.utils.Constants

class PokemonViewHolder(private val binding: ItemPokemonListBinding) : ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon) {
        binding.pokemon = pokemon
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, PokedexDetailActivity::class.java)
            intent.putExtra(Constants.POKEMON_ID, pokemon.name)
            binding.root.context.startActivity(intent)
        }
        binding.executePendingBindings()
    }
}