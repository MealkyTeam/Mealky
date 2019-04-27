package com.teammealky.mealky.presentation.addmeal.model

data class MealViewModel(val title: String, val preparationTime: String, val description: String) {
    companion object {
        fun basicMealViewModel() = MealViewModel("", "", "")
    }
}