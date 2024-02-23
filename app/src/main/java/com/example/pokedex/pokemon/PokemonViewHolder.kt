package com.example.pokedex.pokemon

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pokedex.PokemonInfoActivity
import com.example.pokedex.databinding.ItemPokemonListBinding
import com.example.pokedex.network.models.Pokemon
import com.example.pokedex.utils.Constants

class PokemonViewHolder(private val binding: ItemPokemonListBinding) : ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon) {
        binding.apply {
            this.pokemon = pokemon
            root.setOnClickListener {
                val intent = Intent(root.context, PokemonInfoActivity::class.java)
                intent.putExtra(Constants.POKEMON_NAME, pokemon.name)
                root.context.startActivity(intent)
            }
            Glide.with(root)
                .load(pokemon.imageUrl)
                .into(imageViewPokemon)
        }
        binding.executePendingBindings()
    }
}