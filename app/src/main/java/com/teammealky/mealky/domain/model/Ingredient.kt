package com.teammealky.mealky.domain.model


data class Ingredient(
        val id: Int,
        val name: String,
        val unit: Unit,
        val quantity: Double
)