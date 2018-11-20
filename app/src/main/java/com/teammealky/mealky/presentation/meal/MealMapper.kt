package com.teammealky.mealky.presentation.meal

import android.os.Bundle
import com.teammealky.mealky.domain.model.Category
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Unit
import com.teammealky.mealky.domain.model.User

object MealMapper {

    fun writeExtra(model: Meal): Bundle {
        val bundle = Bundle()

        bundle.putInt(ID, model.id)
        bundle.putString(NAME, model.name)
        bundle.putInt(PREP_TIME, model.prepTime)
        bundle.putString(PREPARATION, model.preparation)
        bundle.putAll(writeIngredients(model.ingredients))
        bundle.putAll(writeCategories(model.categories))
        bundle.putBoolean(CONFIRMED, model.confirmed)
        bundle.putStringArrayList(IMAGES, ArrayList(model.images))
        bundle.putAll(writeAuthor(model.author))

        return bundle
    }

    private fun writeIngredients(ingredients: List<Ingredient>): Bundle {
        val bundle = Bundle()
        bundle.putIntArray(INGREDIENT_ID, ingredients.map { it.id }.toIntArray())
        bundle.putStringArray(INGREDIENT_NAME, ingredients.map { it.name }.toTypedArray())
        bundle.putAll(writeUnits(ingredients.map { it.unit }))
        bundle.putDoubleArray(INGREDIENT_QUANTITY, ingredients.map { it.quantity }.toDoubleArray())

        return bundle
    }

    private fun writeUnits(units: List<Unit>): Bundle {
        val bundle = Bundle()
        bundle.putIntArray(UNIT_ID, units.map { it.id }.toIntArray())
        bundle.putStringArray(UNIT_NAME, units.map { it.name }.toTypedArray())

        return bundle
    }

    private fun writeCategories(categories: List<Category>): Bundle {
        val bundle = Bundle()
        bundle.putIntArray(CATEGORY_ID, categories.map { it.id }.toIntArray())
        bundle.putStringArray(CATEGORY_NAME, categories.map { it.name }.toTypedArray())

        return bundle
    }

    private fun writeAuthor(author: User): Bundle {
        val bundle = Bundle()
        bundle.putInt(AUTHOR_ID, author.id)
        bundle.putString(AUTHOR_EMAIL, author.email)
        bundle.putString(AUTHOR_PASSWORD, author.password)
        bundle.putString(AUTHOR_USERNAME, author.username)

        return bundle
    }

    private fun readIngredients(bundle: Bundle?): List<Ingredient> {
        bundle?.let {
            val ids = it.getIntArray(INGREDIENT_ID) ?: intArrayOf()
            val names = it.getStringArray(INGREDIENT_NAME) ?: emptyArray()
            val units = readUnits(bundle)
            val quantities = it.getDoubleArray(INGREDIENT_QUANTITY) ?: doubleArrayOf()

            val ingredients = mutableListOf<Ingredient>()
            for (i in ids.indices)
                ingredients.add(
                        Ingredient(
                                ids[i],
                                names[i],
                                units[i],
                                quantities[i]
                        )
                )

            return ingredients
        }

        return emptyList()
    }

    private fun readUnits(bundle: Bundle?): List<Unit> {
        bundle?.let {
            val ids = it.getIntArray(UNIT_ID) ?: intArrayOf()
            val names = it.getStringArray(UNIT_NAME) ?: emptyArray()

            val units = mutableListOf<Unit>()
            for (i in ids.indices)
                units.add(Unit(ids[i], names[i]))

            return units
        }
        return emptyList()
    }

    private fun readCategories(bundle: Bundle?): List<Category> {
        bundle?.let {
            val ids = it.getIntArray(CATEGORY_ID) ?: intArrayOf()
            val names = it.getStringArray(CATEGORY_NAME) ?: emptyArray()

            val categories = mutableListOf<Category>()
            for (i in ids.indices)
                categories.add(Category(ids[i], names[i]))

            return categories
        }
        return emptyList()
    }

    private fun readAuthor(bundle: Bundle?): User {
        bundle?.let {
            val id = bundle.getInt(AUTHOR_ID)
            val email = bundle.getString(AUTHOR_EMAIL)
            val password = bundle.getString(AUTHOR_PASSWORD)
            val username = bundle.getString(AUTHOR_USERNAME, "")

            return User(id, email, password, username)
        }

        return User.defaultUser()
    }

    fun readExtra(bundle: Bundle?): Meal? {
        if (null == bundle) return null

        val id = bundle.getInt(ID)
        val name = bundle.getString(NAME, "")
        val prepTime = bundle.getInt(PREP_TIME)
        val preparation = bundle.getString(PREPARATION, "")
        val ingredients = readIngredients(bundle)
        val categories = readCategories(bundle)
        val confirmed = bundle.getBoolean(CONFIRMED, true)
        val images = bundle.getStringArrayList(IMAGES) ?: ArrayList()
        val author = readAuthor(bundle)

        return Meal(id, name, prepTime, preparation, ingredients, categories, confirmed, images, author)
    }

    private const val ID = "id"
    private const val NAME = "name"
    private const val PREP_TIME = "prepTime"
    private const val PREPARATION = "preparation"

    private const val INGREDIENT_ID = "ingredient_id"
    private const val INGREDIENT_NAME = "ingredient_name"
    private const val UNIT_ID = "unit_id"
    private const val UNIT_NAME = "unit_name"
    private const val INGREDIENT_QUANTITY = "ingredient_quantity"

    private const val CATEGORY_ID = "category_id"
    private const val CATEGORY_NAME = "category_name"

    private const val CONFIRMED = "confirmed"
    private const val IMAGES = "images"

    private const val AUTHOR_ID = "author_id"
    private const val AUTHOR_USERNAME = "author_username"
    private const val AUTHOR_EMAIL = "author_email"
    private const val AUTHOR_PASSWORD = "author_password"
}
