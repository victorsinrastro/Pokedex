package com.example.pokedex.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.pokedex.databinding.ItemPokemonListBinding
import com.example.pokedex.network.models.Pokemon

class PokemonAdapter( var pokemonList: List<Pokemon>) :
    Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonListBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
    }
}