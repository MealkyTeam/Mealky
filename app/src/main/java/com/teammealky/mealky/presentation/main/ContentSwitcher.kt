package com.teammealky.mealky.presentation.main

import android.os.Bundle
import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.discover.DiscoverFragment
import com.teammealky.mealky.presentation.meals.MealListFragment
import com.teammealky.mealky.presentation.settings.SettingsFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListFragment

class ContentSwitcher(private val fm: FragmentManager) {

    private val homeFragmentBundle = Bundle()

    fun switchContent(newFragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>?) {
        val currentFragment = getCurrentFragment()

        if (areTheSame(newFragment)) {
            updateFragment(newFragment)
            return
        }

        val ft = fm.beginTransaction()
        ft.setReorderingAllowed(true)
        if (cleanStack) {
            val stackEntryAt = if (isSecondLevel(newFragment)) 1 else 0
            cleanBackStack(stackEntryAt)
        }

        if (isFirstLevel(currentFragment) && currentFragment != null) {
            saveHome(currentFragment)
        }

        if (isFirstLevel(newFragment)) {
            restoreHome(newFragment, ft)
        } else {
            ft.replace(R.id.containerMain, newFragment)
        }

        ft.addToBackStack(null)
        ft.commit()
    }

    private fun updateFragment(newFragment: Fragment) {
        when (newFragment) {
            is MealListFragment -> {
                (getCurrentFragment() as MealListFragment).onNewArguments(newFragment.arguments)
            }
        }
    }

    private fun areTheSame(newFragment: Fragment) = getCurrentFragment()?.javaClass == newFragment.javaClass

    private fun restoreHome(newFragment: Fragment, ft: FragmentTransaction) {
        val rlNewFragment = fm.getFragment(homeFragmentBundle, Navigator.FRAG_HOME) ?: newFragment
        ft.replace(R.id.containerMain, rlNewFragment)
    }

    private fun saveHome(currentFragment: Fragment) {
        fm.putFragment(homeFragmentBundle, Navigator.FRAG_HOME, currentFragment)
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

    private fun isFirstLevel(fragment: Fragment?): Boolean = fragment is MealListFragment

    fun getCurrentFragment(): Fragment? = fm.findFragmentById(R.id.containerMain)

}