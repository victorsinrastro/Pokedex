package com.example.pokedex.pokemon

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pokedex.PokemonInfoActivity
import com.example.pokedex.R
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
            if (pokemon.types.isNotEmpty()) {
                textViewPokemonFirstType.setBackgroundColor(determineColorFromType(pokemon.types[0].type.name))
                if (pokemon.types.size > 1) {
                    textViewPokemonSecondType.setBackgroundColor(determineColorFromType(pokemon.types[1].type.name))
                }
            }
            Glide.with(root)
                .load(pokemon.imageUrl)
                .into(imageViewPokemon)
        }
        binding.executePendingBindings()
    }

    private fun determineColorFromType(type: String): Int {
        val context = binding.root.context
        return when (type) {
            PokemonType.GRASS -> ContextCompat.getColor(context, R.color.pokemon_grass)
            PokemonType.POISON -> ContextCompat.getColor(context, R.color.pokemon_poison)
            PokemonType.FIRE -> ContextCompat.getColor(context, R.color.pokemon_fire)
            PokemonType.FLYING -> ContextCompat.getColor(context, R.color.pokemon_flying)
            PokemonType.WATER -> ContextCompat.getColor(context, R.color.pokemon_water)
            PokemonType.BUG -> ContextCompat.getColor(context, R.color.pokemon_bug)
            PokemonType.NORMAL -> ContextCompat.getColor(context, R.color.pokemon_normal)
            else -> ContextCompat.getColor(context, R.color.background_light_grey)
        }
    }

    private object PokemonType {
        const val GRASS = "grass"
        const val POISON = "poison"
        const val FIRE = "fire"
        const val FLYING = "flying"
        const val WATER = "water"
        const val BUG = "bug"
        const val NORMAL = "normal"
    }
}