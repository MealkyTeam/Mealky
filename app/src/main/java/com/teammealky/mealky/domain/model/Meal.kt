package com.teammealky.mealky.domain.model

import com.google.gson.annotations.SerializedName

data class Meal(val id: Int,
                val name: String,
                @SerializedName("prep_time") val prepTime: Int,
                val preparation: String,
                val images: List<String>,
                val confirmed: Boolean,
                val author: User,
                val categories: List<Category>,
                val ingredients: List<Ingredient>
)