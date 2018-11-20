package com.teammealky.mealky.domain.model

import com.google.gson.annotations.SerializedName

data class Meal(val id: Int,
                val name: String,
                @SerializedName("prep_time") val prepTime: Int,
                val preparation: String,
        //todo ingredient needs to be added in api first
                val ingredients: List<Ingredient>,
                val categories: List<Category>,
                val confirmed: Boolean,
                val images: List<String>,
                val author: User
)