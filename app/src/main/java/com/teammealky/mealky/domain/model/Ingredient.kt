package com.teammealky.mealky.domain.model


data class Ingredient(
        val name: String,
        val unit: Unit,
        val quantity: Double
) {

    companion object {
        fun defaultIngredient(): Ingredient {
            return Ingredient("", Unit(""), 0.0)
        }

        fun isSameIngredientWithDifferentQuantity(first: Ingredient, second: Ingredient): Boolean {
            return first.unit == second.unit && first.name == second.name
        }
    }
}