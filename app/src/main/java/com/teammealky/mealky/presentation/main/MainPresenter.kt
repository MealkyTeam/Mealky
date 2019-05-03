package com.teammealky.mealky.presentation.main

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject


class MainPresenter @Inject constructor(

) : BasePresenter<MainPresenter.UI>() {

    fun setContent(menuItemId: Int) {
        when (menuItemId) {
            R.id.navHome -> ui().perform { it.openHome() }
            R.id.navShoppingList -> ui().perform { it.openShoppingList() }
            R.id.navDiscover -> ui().perform { it.openDiscover() }
            R.id.navSettings -> ui().perform { it.openSettings() }
        }
    }

    fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>? = null) {
        ui().perform { it.setContent(fragment, cleanStack, transitionValues) }
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
        fun openHome()
        fun openShoppingList()
        fun openDiscover()
        fun openSettings()
    }
}