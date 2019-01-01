package com.teammealky.mealky.presentation.shoppinglist.model

import com.teammealky.mealky.domain.model.Ingredient

data class ShoppingListItemViewModel(var item: Ingredient, var isGreyedOut: Boolean)

