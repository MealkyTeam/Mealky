package com.teammealky.mealky.presentation.account

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AccountPresenter @Inject constructor() : BasePresenter<AccountPresenter.UI>() {

    override fun attach(ui: UI) {
        super.attach(ui)
        ui().perform { it.toSignIn() }
    }

    interface UI : BaseUI {
        fun toSignIn()
    }
}