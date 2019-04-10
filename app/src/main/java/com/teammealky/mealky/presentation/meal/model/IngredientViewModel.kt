package com.teammealky.mealky.presentation.meal.model

import com.teammealky.mealky.domain.model.Ingredient

data class IngredientViewModel(var item: Ingredient, var isChecked: Boolean)