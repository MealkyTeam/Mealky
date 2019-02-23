package com.teammealky.mealky.presentation.main

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.discover.DiscoverFragment
import com.teammealky.mealky.presentation.meals.MealListFragment
import com.teammealky.mealky.presentation.settings.SettingsFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListFragment
import javax.inject.Inject


class MainPresenter @Inject constructor(

) : BasePresenter<MainPresenter.UI>() {

    fun setContent(menuItemId: Int) {
        var fragment: Fragment? = null
        when (menuItemId) {
            R.id.navHome -> fragment = MealListFragment()
            R.id.navShoppingList -> fragment = ShoppingListFragment()
            R.id.navDiscover -> fragment = DiscoverFragment()
            R.id.navSettings -> fragment = SettingsFragment()
        }
        fragment?.let { setContent(it, false) }
    }

    fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>? = null) {
        ui().perform { it.setContent(fragment, cleanStack, transitionValues) }
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
    }
}