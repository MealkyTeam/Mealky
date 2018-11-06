package com.teammealky.mealky.presentation.commons

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.meal.MealFragment
import com.teammealky.mealky.presentation.meals.MealListFragment

class Navigator(private val nav: Navigable) {
    interface Navigable {
        fun getContext(): Context
        fun navigateTo(fragment: Fragment, cleanStack: Boolean = false)
    }

    init {
        App.get(nav.getContext()).getComponent().inject(this)
    }

    fun open(intent: Intent) {
        val destination = intent.getStringExtra(NAVIGATE)
        destination?.let {
            when (it) {
//                FRAG_SHOPPING_LIST -> openShoppingList()
//                FRAG_MEAL -> {
//                    try {
//                        MealMapper.readExtra(intent.extras)?.let { model ->
//                            open(model)
//                        }
//                    } catch (ignored: Exception) {
//
//                    }
//                }
                else -> {
                }
            }
        }
    }

    fun openMeal(meal: Meal){
        val fragment = MealFragment.newInstance(meal)

        nav.navigateTo(fragment)
    }

    fun openHome() {
        nav.navigateTo(MealListFragment(), true)
    }

    companion object {
        const val NAVIGATE = "navigate_to"
        const val FRAG_MEAL = "meal"

        fun from(navigable: Navigator.Navigable): Navigator = Navigator(navigable)
    }
}
