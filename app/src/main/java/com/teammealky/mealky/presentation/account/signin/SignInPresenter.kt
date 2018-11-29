package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SignInPresenter @Inject constructor(
) : BasePresenter<SignInPresenter.UI>() {

    interface UI : BaseUI {
    }
}