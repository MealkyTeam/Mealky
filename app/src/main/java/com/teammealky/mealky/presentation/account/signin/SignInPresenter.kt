package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signin.SignInWithPasswordUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class SignInPresenter @Inject constructor(private val signInWithPasswordUseCase: SignInWithPasswordUseCase
) : BasePresenter<SignInPresenter.UI>() {

    var model = User.defaultUser()

    fun signInButtonClicked() {
        if (invalidUser()) {
            ui().perform { it.setErrorOnEmail() }
            return
        }

        ui().perform {
            it.hideKeyboard()
            it.hideEmailError()
            it.isLoading(true)
        }

        disposable.add(signInWithPasswordUseCase.execute(
                //todo fix it its only for test
                SignInWithPasswordUseCase.Params("admin", "123456"),
                { token ->
                    ui().perform {
                        it.isLoading(false)
                        model = model.copy(token = token)
                        changeActivity()
                    }
                },
                { e ->
                    ui().perform { it.isLoading(false) }
                    Timber.d("KUBA Method:signInButtonClicked ***** ERROR: $e *****")
                    Timber.d("KUBA Method:signInButtonClicked ***** ${(e as HttpException).message()} *****")
                })
        )
//        when (response) {
//            Response.CONFIRM_EMAIL -> ui().perform {
//                it.showNeedConfirmEmail()
//            }
//            Response.NO_SUCH_USER -> ui().perform {
//                it.showThereIsNoSuchUser()
//            }
//            Response.INVALID_CREDENTIALS -> ui().perform {
//                it.showInvalidCredentials()
//            }
//            else -> changeActivity()
//        }
    }

    private fun changeActivity() {
        ui().perform {
            it.saveUser(model)
            it.toMainActivity()
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
        fun saveUser(user: User)
    }
}