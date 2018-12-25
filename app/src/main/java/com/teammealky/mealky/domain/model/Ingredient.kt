package com.teammealky.mealky.domain.model


data class Ingredient(
        val id: Int,
        val name: String,
        val unit: Unit,
        val quantity: Double
) {

    companion object {
        fun defaultIngredient(): Ingredient {
            return Ingredient(-1, "", Unit(-1, ""), 0.0)
        }
    }
}