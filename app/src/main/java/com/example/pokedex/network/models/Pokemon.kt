package com.example.pokedex.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.database.Converters

@Entity(tableName = "pokemon_table")
@TypeConverters(Converters::class)
data class Pokemon(
    @PrimaryKey val name: String,
    var id: Int,
    var weight: Int,
    var height: Int,
    var abilities: List<Abilities>,
    var types: List<Types>,
    var imageUrl: String
) {
    init {
        require(id >= 0) { "ID must be non-negative" }
        require(weight >= 0) { "Weight must be non-negative" }
        require(height >= 0) { "Height must be non-negative" }
        require(name.isNotEmpty()) { "Name must not be empty" }
        require(imageUrl.isNotEmpty()) { "Image URL must not be empty" }
    }
}
