package com.teammealky.mealky.presentation.account.forgottenPassword

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class ForgottenPasswordPresenter @Inject constructor(
) : BasePresenter<ForgottenPasswordPresenter.UI>() {

    interface UI : BaseUI {
        fun isLoading(isLoading: Boolean)

    }
}