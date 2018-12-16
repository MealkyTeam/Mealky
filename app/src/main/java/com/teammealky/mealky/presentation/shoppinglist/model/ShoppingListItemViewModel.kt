package com.teammealky.mealky.presentation.shoppinglist.model

import com.teammealky.mealky.domain.model.Ingredient

data class ShoppingListItemViewModel(val item: Ingredient, var isGreyedOut: Boolean, val position: Int)

