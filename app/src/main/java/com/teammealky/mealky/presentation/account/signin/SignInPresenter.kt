package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SignInPresenter @Inject constructor(
) : BasePresenter<SignInPresenter.UI>() {

    fun signInButtonClicked() {
        if (authorized())
            ui().perform { it.toMainActivity() }
        else
            ui().perform { it.showInvalidCredentials() }
    }

    fun forgottenPasswordLinkClicked() {
        ui().perform { it.toForgottenPasswordFragment() }
    }

    fun signUpLinkClicked() {
        ui().perform { it.toSignUpFragment() }
    }

    private fun authorized(): Boolean {
        //todo need logic
        return true
    }


    interface UI : BaseUI {
        fun toMainActivity()
        fun toSignUpFragment()
        fun toForgottenPasswordFragment()
        fun showInvalidCredentials()
    }
}