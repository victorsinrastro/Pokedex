package com.example.pokedex.pokemon

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokedex.databinding.ItemPokemonListBinding
import com.example.pokedex.network.models.Pokemon

class PokemonViewHolder(private val binding: ItemPokemonListBinding) : ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon) {
        binding.pokemon = pokemon
        binding.executePendingBindings()
    }
}