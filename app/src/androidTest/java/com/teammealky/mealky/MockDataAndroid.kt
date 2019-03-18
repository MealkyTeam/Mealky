package com.teammealky.mealky

import com.teammealky.mealky.domain.model.Category
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel

class MockDataAndroid {
    companion object {
        val UNITS = listOf(
                Unit("unit1"),
                Unit("unit2"),
                Unit("unit3")
        )
        val INGREDIENTS = listOf(
                Ingredient("ingr1", UNITS[0], 1.0),
                Ingredient("ingr2", UNITS[1], 2.0),
                Ingredient("ingr3", UNITS[2], 3.0)
        )
        val CATEGORIES = listOf(
                Category(1, "category1"),
                Category(2, "category2"),
                Category(3, "category3")
        )

        val IMAGES = listOf(
                "img1",
                "img2",
                "img3"
        )

        val USERS = listOf(
                User(1, "email1", "password1", "username1", "token1"),
                User(2, null, null, "username2", "token2"),
                User(3, "email3", null, "username3", "token3")
        )

        val MEALS = listOf(
                Meal(1, "meal1", 1, "prep1", listOf(IMAGES[0], IMAGES[2]),
                        true, USERS[0], listOf(CATEGORIES[0], CATEGORIES[2]), listOf(INGREDIENTS[0], INGREDIENTS[2])),
                Meal(2, "meal2", 2, "prep2", listOf(IMAGES[1], IMAGES[2]),
                        true, USERS[1], listOf(CATEGORIES[1], CATEGORIES[2]), listOf(INGREDIENTS[1], INGREDIENTS[2])),
                Meal(3, "meal3", 3, "prep3", listOf(IMAGES[0], IMAGES[1]),
                        true, USERS[2], listOf(CATEGORIES[0], CATEGORIES[1]), listOf(INGREDIENTS[0], INGREDIENTS[1]))
        )

        val MEALS_PAGE = Page(
                MEALS,
                1,
                MEALS.size,
                true,
                true,
                true,
                MEALS.size,
                0
        )

        val SHOPPING_LIST_ITEM_VIEW_MODEL =
                INGREDIENTS.map { item -> ShoppingListItemViewModel(item, false) }
    }
}