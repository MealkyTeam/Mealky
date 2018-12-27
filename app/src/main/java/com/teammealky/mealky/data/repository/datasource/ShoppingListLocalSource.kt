package com.teammealky.mealky.data.repository.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListLocalSource @Inject constructor(
        @ApplicationContext private val context: Context,
        private val parser: Gson
) : ShoppingListRepository {

    private var cache: ArrayList<Ingredient>? = null
    private val listType: Type = object : TypeToken<List<Ingredient>>() {}.type

    override fun list(): Single<List<Ingredient>> = Single.fromCallable { getItems() }

    override fun add(ingredients: List<Ingredient>): Completable = Completable.fromCallable {
        val oldItems = ArrayList(getItems())

        //updated items
        val updatedItems = oldItems.map { ingredient ->
            var updatedItem = ingredients.firstOrNull { Ingredient.isSameIngredientWithDifferentQuantity(it, ingredient) }
            return@map if (updatedItem == null)
                ingredient
            else {
                updatedItem = updatedItem.copy(quantity = updatedItem.quantity + ingredient.quantity)
                updatedItem
            }
        }

        val items = ArrayList(updatedItems)

        //new items
        items.addAll(ingredients.filter { ingredient ->
            oldItems.none { Ingredient.isSameIngredientWithDifferentQuantity(ingredient, it) }
        })
        setItems(items)
    }

    override fun remove(ingredient: Ingredient): Completable = Completable.fromCallable {
        val items = ArrayList(getItems())
        val item = items.firstOrNull { Ingredient.isSameIngredientWithDifferentQuantity(ingredient, it) }
        item?.let {
            items.remove(it)
        }
        setItems(items)
    }

    override fun clear(): Completable = Completable.fromCallable {
        setItems(emptyList())
    }

    private fun getItems(): List<Ingredient> {
        if (null != cache) return cache as List<Ingredient>

        return try {
            val content = readFileContent()
            val items = parse(content)
            cache = ArrayList(items)

            items
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun setItems(newItems: List<Ingredient>) {
        try {
            val newContent = parse(newItems)
            writeFileContent(newContent)

            cache = ArrayList(newItems)
        } catch (e: Exception) {
            Timber.e("KUBA_LOG Method:setItems ***** $e  *****")
        }
    }

    private fun readFileContent(): String {
        return context.openFileInput(STORAGE_NAME).bufferedReader().use {
            it.readText()
        }
    }

    private fun writeFileContent(content: String) {
        context.openFileOutput(STORAGE_NAME, Context.MODE_PRIVATE).use {
            if (content.isEmpty()) it.write("[]".toByteArray())
            else it.write(content.toByteArray())
        }
    }

    private fun parse(items: List<Ingredient>) = parser.toJson(items)

    private fun parse(content: String): List<Ingredient> {
        if (content.isEmpty()) return emptyList()

        var items: List<Ingredient> = emptyList()
        try {
            items = parser.fromJson(content, listType)
        } catch (ignored: Exception) {
        }

        return items
    }

    companion object {
        private const val STORAGE_NAME = "com.teammealky.shoppinglist.1"
    }
}