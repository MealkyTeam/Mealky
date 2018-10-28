package com.kuba.mealky.presentation.main

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.kuba.mealky.R
import com.kuba.mealky.presentation.commons.presenter.BasePresenter
import com.kuba.mealky.presentation.commons.presenter.BaseUI
import com.kuba.mealky.presentation.meals.MealListFragment
import javax.inject.Inject


class MainPresenter @Inject constructor(

) : BasePresenter<MainPresenter.UI>() {

    fun setContent(menuItemId: Int) {
        var fragment: Fragment? = null
        when (menuItemId) {
            //todo add rest of bottomBar menu items
            R.id.navHome -> fragment = MealListFragment()
        }
        fragment?.let { setContent(it, true) }
    }

    fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>? = null) {
        ui().perform { it.setContent(fragment, cleanStack, transitionValues) }
    }

    interface UI : BaseUI {
//        fun openLink(link: Uri)
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
    }
}