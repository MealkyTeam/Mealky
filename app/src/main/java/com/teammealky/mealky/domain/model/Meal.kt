package com.teammealky.mealky.domain.model

data class Meal(val id: Int,
                val name: String,
                val prepTime: Int,
                val preparation: String,
                val images: List<String>
// todo add more models
//                ,
//                val ingredients: List<Ingredient>,
//                val categories: List<Category>
)