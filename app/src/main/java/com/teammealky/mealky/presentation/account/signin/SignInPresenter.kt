package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signin.SignInWithPasswordUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
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
            it.showInfoText(false)
            it.isLoading(true)
        }

        disposable.add(signInWithPasswordUseCase.execute(
                SignInWithPasswordUseCase.Params(model.email ?: "", model.password ?: ""),
                { token ->
                    ui().perform {
                        it.isLoading(false)
                        model = model.copy(token = token.string)
                        changeActivity()
                    }
                },
                { e ->
                    ui().perform {
                        it.showInfoText(true)
                        it.isLoading(false)
                    }
                    if (e is APIError) {
                        when (e.type) {
                            APIError.ErrorType.CONFIRM_EMAIL -> {
                                //todo implement confirm_email
                                Timber.d("KUBA Method:signInButtonClicked *****  confirm email need to be implemented *****")
                            }
                            APIError.ErrorType.NO_SUCH_USER -> {
                                Timber.d("KUBA Method:signInButtonClicked ***** no such *****")
                                ui().perform { it.showThereIsNoSuchUser() }
                            }
                            APIError.ErrorType.INVALID_PASSWORD -> {
                                Timber.d("KUBA Method:signInButtonClicked ***** inv pass *****")

                                ui().perform { it.showInvalidPassword() }
                            }
                            else -> {
                                Timber.d("KUBA Method:signInButtonClicked ***** error *****")
                                ui().perform { it.showErrorMessage(e) }
                            }
                        }
                    }
                    Timber.d("KUBA Method:signInButtonClicked ***** ERROR:$e *****")
                })
        )
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

    interface UI : BaseUI {
        fun isLoading(isLoading: Boolean)
        fun toMainActivity()
        fun toSignUpFragment()
        fun toForgottenPasswordFragment()
        fun showInvalidPassword()
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