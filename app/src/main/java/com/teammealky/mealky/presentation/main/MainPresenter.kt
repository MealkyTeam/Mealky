package com.teammealky.mealky.presentation.main

import androidx.fragment.app.Fragment
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject


class MainPresenter @Inject constructor(

) : BasePresenter<MainPresenter.UI>() {

    fun setContent(menuItemId: Int, invalidateList: Boolean = false) {
        when (menuItemId) {
            R.id.navHome -> ui().perform { it.openHome(invalidateList) }
            R.id.navShoppingList -> ui().perform { it.openShoppingList() }
            R.id.navDiscover -> ui().perform { it.openDiscover() }
            R.id.navSettings -> ui().perform { it.openSettings() }
        }
    }

    fun setContent(fragment: Fragment, cleanStack: Boolean) {
        ui().perform { it.setContent(fragment, cleanStack) }
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false)
        fun openHome(invalidateList: Boolean)
        fun openShoppingList()
        fun openDiscover()
        fun openSettings()
    }
}