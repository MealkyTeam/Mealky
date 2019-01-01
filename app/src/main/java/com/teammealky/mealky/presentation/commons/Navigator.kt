package com.teammealky.mealky.presentation.commons

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.account.AccountActivity
import com.teammealky.mealky.presentation.account.signin.SignInFragment
import com.teammealky.mealky.presentation.account.signup.SignUpFragment
import com.teammealky.mealky.presentation.discover.DiscoverFragment
import com.teammealky.mealky.presentation.main.MainActivity
import com.teammealky.mealky.presentation.meal.MealFragment
import com.teammealky.mealky.presentation.meal.MealMapper
import com.teammealky.mealky.presentation.meals.MealListFragment
import com.teammealky.mealky.presentation.settings.SettingsFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListFragment

class Navigator(private val nav: Navigable) {
    interface Navigable {
        fun getContext(): Context
        fun navigateTo(fragment: Fragment, cleanStack: Boolean = false)
    }

    init {
        App.get(nav.getContext()).getComponent().inject(this)
    }

    fun openFragment(intent: Intent) {
        val destination = intent.getStringExtra(NAVIGATE)
        destination?.let {
            when (it) {
                FRAG_MEAL -> MealMapper.readExtra(intent.extras)?.let { model -> openMeal(model) }
                FRAG_SHOPPING_LIST -> openShoppingList()
                FRAG_DISCOVER -> openDiscover()
                FRAG_SETTINGS -> openSettings()

                FRAG_SIGNIN -> openSignIn(false)
                FRAG_SIGNUP -> openSignUp()
                FRAG_FORGOTTEN_PASSWORD -> openForgottenPassword()
                else -> {
                }
            }
        }
    }

    fun openMeal(meal: Meal) {
        val fragment = MealFragment.newInstance(meal)

        nav.navigateTo(fragment)
    }

    fun openShoppingList() {
        val fragment = ShoppingListFragment()

        nav.navigateTo(fragment)
    }

    fun openDiscover() {
        val fragment = DiscoverFragment()

        nav.navigateTo(fragment)
    }

    fun openSettings() {
        val fragment = SettingsFragment()

        nav.navigateTo(fragment)
    }

    fun openSignIn(withError: Boolean) {
        val fragment = SignInFragment.newInstance(withError)

        nav.navigateTo(fragment)
    }

    fun openSignUp() {
        val fragment = SignUpFragment()

        nav.navigateTo(fragment)
    }

    fun openForgottenPassword() {
        //todo add ForgottenPasswordFragment
    }

    fun openHome() {
        nav.navigateTo(MealListFragment(), true)
    }

    fun openActivity(activityString: String) {
        val context = nav.getContext()
        val activity = when (activityString) {
            ACTIVITY_ACCOUNT -> AccountActivity::class.java
            else -> MainActivity::class.java
        }
        val intent = Intent(context, activity)
        context.startActivity(intent)
    }

    companion object {
        const val NAVIGATE = "navigate_to"

        const val FRAG_MEAL = "meal"
        const val FRAG_SHOPPING_LIST = "shopping_list"
        const val FRAG_DISCOVER = "discover"
        const val FRAG_SETTINGS = "settings"

        const val FRAG_SIGNIN = "signin"
        const val FRAG_SIGNUP = "signup"
        const val FRAG_FORGOTTEN_PASSWORD = "forgotten_password"

        const val ACTIVITY_MAIN = "main"
        const val ACTIVITY_ACCOUNT = "account"

        fun from(navigable: Navigator.Navigable): Navigator = Navigator(navigable)
    }
}
