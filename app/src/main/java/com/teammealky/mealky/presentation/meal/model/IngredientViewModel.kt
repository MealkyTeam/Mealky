package com.teammealky.mealky.presentation.meal.model

import com.teammealky.mealky.domain.model.Ingredient

data class IngredientViewModel(val item: Ingredient, val isChecked: Boolean)