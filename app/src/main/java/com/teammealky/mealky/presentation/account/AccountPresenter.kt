package com.teammealky.mealky.presentation.account

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AccountPresenter @Inject constructor() : BasePresenter<AccountPresenter.UI>() {

    override fun attach(ui: UI) {
        super.attach(ui)
        ui().perform { it.toSignIn() }
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
        fun toSignIn()
    }
}