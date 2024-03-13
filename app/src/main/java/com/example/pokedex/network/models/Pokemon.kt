package com.example.pokedex.network.models

data class Pokemon(
    val name: String,
    var id: Int,
    val weight: Int,
    val height: Int,
    val abilities: List<Abilities>,
    val types: List<Types>,
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
