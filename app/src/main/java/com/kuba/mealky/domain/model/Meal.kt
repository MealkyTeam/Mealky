package com.kuba.mealky.domain.model



data class Meal(val id: Int,
                val name: String,
                val prepTime: Int,
                val preparation: String,
                val images: List<String>
// add more models
//                ,
//                val ingredients: List<Ingredient>,
//                val categories: List<Category>
)