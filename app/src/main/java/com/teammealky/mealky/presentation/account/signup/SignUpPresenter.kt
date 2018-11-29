package com.teammealky.mealky.presentation.account.signup

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
) : BasePresenter<SignUpPresenter.UI>() {

    interface UI : BaseUI {
    }
}