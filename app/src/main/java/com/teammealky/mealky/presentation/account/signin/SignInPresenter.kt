package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class SignInPresenter @Inject constructor(
) : BasePresenter<SignInPresenter.UI>() {

    var model = User.defaultUser()
    var tempCounter = 1

    fun signInButtonClicked() {
        if (invalidUser()) {
            ui().perform { it.setErrorOnEmail() }
            return
        } else
            ui().perform { it.hideEmailError() }

        val response = authorizeResponse()
        if(response == Response.OK){
            ui().perform { it.toMainActivity() }
            return
        }

        tempCounter++
        ui().perform {
            it.showInfoText(true)
            it.hideKeyboard()
        }

        when (response) {
            Response.CONFIRM_EMAIL -> ui().perform {
                it.showNeedConfirmEmail()
            }
            Response.NO_SUCH_USER -> ui().perform {
                it.showThereIsNoSuchUser()
            }
            Response.INVALID_CREDENTIALS -> ui().perform {
                it.showInvalidCredentials()
            }
            else -> ui().perform { it.toMainActivity() }
        }
    }

    private fun invalidUser(): Boolean {
        return !model.hasCorrectEmail()
    }

    fun forgottenPasswordLinkClicked() {
        ui().perform { it.toForgottenPasswordFragment() }
    }

    fun signUpLinkClicked() {
        ui().perform { it.toSignUpFragment() }
    }

    private fun authorizeResponse(): Response {
        //todo need logic
        ui().perform { it.isLoading(true) }

//        val response = Response.getRandom()
        val response = Response.getRandom(tempCounter)
        Timber.e("FunName:authorizeResponse *****$response *****")
        ui().perform { it.isLoading(false) }

        return response
    }

    fun fieldsChanged() {
        if (model.password.isNullOrBlank() || model.email.isNullOrBlank())
            ui().perform { it.toggleSignInButton(false) }
        else
            ui().perform { it.toggleSignInButton(true) }
    }

    enum class Response {
        OK, CONFIRM_EMAIL, NO_SUCH_USER, INVALID_CREDENTIALS;

        companion object {
            fun getRandom(i: Int): Response {
                return when (i % 4) {
                    1 -> CONFIRM_EMAIL
                    2 -> NO_SUCH_USER
                    3 -> INVALID_CREDENTIALS
                    else -> OK
                }
            }
        }
    }

    interface UI : BaseUI {
        fun isLoading(isLoading: Boolean)
        fun toMainActivity()
        fun toSignUpFragment()
        fun toForgottenPasswordFragment()
        fun showInvalidCredentials()
        fun showThereIsNoSuchUser()
        fun showNeedConfirmEmail()
        fun toggleSignInButton(toggle: Boolean)
        fun showInfoText(isVisible: Boolean)
        fun setErrorOnEmail()
        fun hideEmailError()
        fun hideKeyboard()
    }
}