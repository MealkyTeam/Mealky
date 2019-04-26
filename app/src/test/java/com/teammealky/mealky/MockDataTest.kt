package com.teammealky.mealky

import com.teammealky.mealky.domain.model.Category
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import java.io.File

class MockDataTest {
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
                User(1, "validEmail@wp.com", "password1", "username1", "token1"),
                User(2, null, null, "username2", "token2"),
                User(3, "invalidEmail", "PsInv", "username3", "token3")
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

        val MEALS_PAGE2 = Page(
                MEALS.reversed(),
                2,
                MEALS.size,
                true,
                false,
                true,
                MEALS.size,
                0
        )

        val MEALS_EMPTY_PAGE = Page(
                emptyList<Meal>(),
                1,
                0,
                true,
                true,
                true,
                0,
                0
        )

        val THUMBNAIL_IMAGE = ThumbnailImage(-1, File("filepath"))

        val INGREDIENT_VIEW_MODEL =
                INGREDIENTS.map { item -> IngredientViewModel(item, false) }

        val CORRECT_TOKEN = Token("Correct token")
        val WRONG_TOKEN = Token("Wrong token")

        val NOT_EMPTY_QUERY_WITH_RESULT = "Some query"
        val NOT_EMPTY_QUERY_WITHOUT_RESULT = "Some query. No meals found!"
    }
}