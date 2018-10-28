package com.kuba.mealky.presentation.main

import android.content.Context
import android.os.Bundle
import android.transition.TransitionValues
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuba.mealky.R
import com.kuba.mealky.presentation.App
import com.kuba.mealky.presentation.commons.Navigator
import com.kuba.mealky.presentation.commons.extension.*
import com.kuba.mealky.presentation.commons.presenter.BaseActivity
import com.kuba.mealky.presentation.meal.MealFragment
import com.kuba.mealky.presentation.meals.MealListFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.lang.Exception

class MainActivity : BaseActivity<MainPresenter, MainPresenter.UI, MainViewModel>(),
        MainPresenter.UI, Navigator.Navigable, BottomNavigationView.OnNavigationItemSelectedListener {

    override val vmClass = MainViewModel::class.java

    private val contentSwitcher = ContentSwitcher(supportFragmentManager, this)
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onPresenterPrepared()
        this.savedInstanceState = savedInstanceState
        initUI()
    }

    private fun initUI() {
        bottomBar.setOnNavigationItemSelectedListener(this)

        if (null == savedInstanceState) bottomBar.markAsSelected(BOTTOM_BAR_MEALS)
    }

    override fun getContext(): Context = this

    override fun navigateTo(fragment: Fragment, cleanStack: Boolean) {
        presenter?.setContent(fragment, cleanStack)
    }

    override fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>?) {
        contentSwitcher.switchContent(fragment, cleanStack, transitionValues)
        updateBottomBarSelection(fragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter?.setContent(item.itemId)
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            try {
                supportFragmentManager.popBackStackImmediate()
            } catch (ignored: Exception) {
            }
            contentSwitcher.getCurrentFragment()?.let { currentFragment ->
                updateBottomBarSelection(currentFragment)
            }
        } else {
            finish()
        }
    }

    private fun updateBottomBarSelection(fragment: Fragment): Boolean {
        var isBottomBarFragment = true
        var menuItemPosition = BOTTOM_BAR_NONE
        when (fragment) {
            is MealListFragment -> menuItemPosition = BOTTOM_BAR_MEALS
            is MealFragment -> menuItemPosition = BOTTOM_BAR_MEALS

            else -> isBottomBarFragment = false
        }
        if (menuItemPosition == BOTTOM_BAR_NONE) bottomBar.clearSelection()
        else bottomBar.markAsSelected(menuItemPosition)

        return isBottomBarFragment
    }

    private fun onPresenterPrepared() {
        val setDefaultContent = (null == contentSwitcher.getCurrentFragment())
        if (setDefaultContent) {
            presenter?.setContent(R.id.navHome)
        }
    }

    companion object {
        private const val BOTTOM_BAR_NONE = -1
        private const val BOTTOM_BAR_MEALS = 0
        private const val BOTTOM_BAR_SHOPPING_LIST = 1
        private const val BOTTOM_BAR_DISCOVER = 2
        private const val BOTTOM_BAR_SETTINGS = 3
    }
}


