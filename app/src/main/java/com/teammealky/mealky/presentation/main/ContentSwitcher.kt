package com.teammealky.mealky.presentation.main

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.discover.DiscoverFragment
import com.teammealky.mealky.presentation.meals.MealListFragment
import com.teammealky.mealky.presentation.settings.SettingsFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListFragment

class ContentSwitcher(private val fm: FragmentManager) {

    fun switchContent(newFragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>?) {
        val currentFragment = getCurrentFragment()

        if(currentFragment?.javaClass == newFragment.javaClass)
            return

        if (isFirstLevel(newFragment) && null != currentFragment) {
            performRewind()
            return
        }

        val ft = fm.beginTransaction()
        ft.setReorderingAllowed(true)
        if (cleanStack) {
            val stackEntryAt = if (isSecondLevel(newFragment)) 1 else 0
            cleanBackStack(stackEntryAt)
        }
        ft.replace(R.id.containerMain, newFragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun performRewind() {
        cleanBackStack(1, true)
    }

    private fun isSecondLevel(fragment: Fragment): Boolean {
        return when (fragment) {
            is ShoppingListFragment -> true
            is DiscoverFragment -> true
            is SettingsFragment -> true
            else -> false
        }
    }

    private fun cleanBackStack(stackEntryAt: Int, immediately: Boolean = false) {
        if (fm.backStackEntryCount <= stackEntryAt) return
        if (fm.backStackEntryCount <= 0) return

        val first = fm.getBackStackEntryAt(stackEntryAt)
        if (immediately)
            fm.popBackStackImmediate(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        else
            fm.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

    private fun isFirstLevel(fragment: Fragment): Boolean = fragment is MealListFragment

    fun getCurrentFragment(): Fragment? = fm.findFragmentById(R.id.containerMain)

}