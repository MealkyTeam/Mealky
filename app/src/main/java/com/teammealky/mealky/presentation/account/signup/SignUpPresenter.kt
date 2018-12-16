package com.teammealky.mealky.presentation.account.signup

import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signup.SignUpUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
        private val signUpUseCase: SignUpUseCase
) : BasePresenter<SignUpPresenter.UI>() {

    var model = User.defaultUser()

    fun signUpButtonClicked() {
        hideErrors()
        val errors = validUser()
        showErrors(errors)

        if (errors.isNotEmpty())
            return

        ui().perform {
            it.hideKeyboard()
            it.showInfoTv(false)
            it.isLoading(true)
        }

        disposable.add(signUpUseCase.execute(
                SignUpUseCase.Params(model.username ?: "", model.email ?: "", model.password ?: ""),
                { _ ->
                    ui().perform {
                        it.isLoading(false)
                        it.toSignInFragment()
                    }
                },
                { e ->
                    ui().perform {
                        it.showInfoTv(true)
                        it.isLoading(false)
                    }
                    if (e is APIError) {
                        when (e.type) {
                            APIError.ErrorType.EMAIL_TAKEN -> {
                                ui().perform { it.showErrorInInfo(APIError.ErrorType.EMAIL_TAKEN) }
                            }
                            APIError.ErrorType.USERNAME_TAKEN -> {
                                ui().perform { it.showErrorInInfo(APIError.ErrorType.USERNAME_TAKEN) }
                            }
                            APIError.ErrorType.INVALID_EMAIL -> {
                                ui().perform { it.showErrorInInfo(APIError.ErrorType.INVALID_EMAIL) }
                            }
                            APIError.ErrorType.INVALID_USERNAME -> {
                                ui().perform { it.showErrorInInfo(APIError.ErrorType.INVALID_USERNAME) }
                            }
                            APIError.ErrorType.INVALID_PASSWORD -> {
                                ui().perform { it.showErrorInInfo(APIError.ErrorType.INVALID_PASSWORD) }
                            }
                            else -> {
                                ui().perform { it.showErrorMessage(e) }
                            }
                        }
                    }
                    Timber.e("KUBA Method:signUpButtonClicked ***** ERROR:$e *****")
                })
        )
    }

    private fun showErrors(errors: List<APIError.ErrorType>) {
        for (error in errors) {
            when (error) {
                APIError.ErrorType.INVALID_USERNAME -> ui().perform { it.toggleUsernameError(true) }
                APIError.ErrorType.INVALID_EMAIL -> ui().perform { it.toggleEmailError(true) }
                APIError.ErrorType.INVALID_PASSWORD -> ui().perform { it.togglePasswordError(true) }
                else -> ui().perform { }
            }
        }
    }

    private fun hideErrors() {
        ui().perform {
            it.toggleUsernameError(false)
            it.toggleEmailError(false)
            it.togglePasswordError(false)
        }
    }

    private fun validUser(): List<APIError.ErrorType> {
        val list = mutableListOf<APIError.ErrorType>()
        when {
            !model.hasCorrectUsername() -> list.add(APIError.ErrorType.INVALID_USERNAME)
            !model.hasCorrectEmail() -> list.add(APIError.ErrorType.INVALID_EMAIL)
            !model.hasCorrectPassword() -> list.add(APIError.ErrorType.INVALID_PASSWORD)
        }

        return list
    }

    fun signInLinkClicked() {
        ui().perform { it.toSignInFragment() }
    }

    fun fieldsChanged() {
        if (model.password.isNullOrBlank() || model.email.isNullOrBlank() || model.username.isNullOrBlank())
            ui().perform { it.toggleSignUpButton(false) }
        else
            ui().perform { it.toggleSignUpButton(true) }
    }

    interface UI : BaseUI {
        fun isLoading(isLoading: Boolean)
        fun toSignInFragment()
        fun showErrorInInfo(error: APIError.ErrorType)
        fun showInfoTv(isVisible: Boolean)
        fun toggleSignUpButton(toggle: Boolean)
        fun toggleUsernameError(toggle: Boolean)
        fun toggleEmailError(toggle: Boolean)
        fun togglePasswordError(toggle: Boolean)
    }
}