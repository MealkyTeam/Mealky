package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signin.SignInWithPasswordUseCase
import com.teammealky.mealky.domain.usecase.token.SaveTokenUseCase
import com.teammealky.mealky.domain.usecase.user.SaveUserUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SignInPresenter @Inject constructor(
        private val signInWithPasswordUseCase: SignInWithPasswordUseCase,
        private val saveUserUseCase: SaveUserUseCase,
        private val saveTokenUseCase: SaveTokenUseCase
) : BasePresenter<SignInPresenter.UI>() {

    var model = User.defaultUser()

    fun signInButtonClicked() {
        if (!validUser()) {
            ui().perform { it.setErrorOnEmail() }
            return
        }

        ui().perform {
            it.hideEmailError()
            it.showInfoTv(false)
            it.isLoading(true)
        }

        disposable.add(signInWithPasswordUseCase.execute(
                SignInWithPasswordUseCase.Params(model.email ?: "", model.password ?: ""),
                { user ->
                    ui().perform {
                        it.isLoading(false)
                        model = model.copy(id = user.id, token = user.token, username = user.username)
                        changeActivity()
                    }
                },
                { e ->
                    ui().perform {
                        it.showInfoTv(true)
                        it.isLoading(false)
                    }
                    if (e is APIError) {
                        showError(e)
                    } else {
                        ui().perform { it.showErrorMessage({ signInButtonClicked() }, e) }
                    }
                })
        )
    }

    private fun showError(e: APIError) {
        when (e.type) {
            APIError.ErrorType.CONFIRM_EMAIL -> {
                ui().perform { it.showErrorInfo(APIError.ErrorType.CONFIRM_EMAIL) }
            }
            APIError.ErrorType.NO_SUCH_USER -> {
                ui().perform { it.showErrorInfo(APIError.ErrorType.NO_SUCH_USER) }
            }
            APIError.ErrorType.WRONG_PASSWORD -> {
                ui().perform { it.showErrorInfo(APIError.ErrorType.WRONG_PASSWORD) }
            }
            else -> {
                ui().perform { it.showErrorMessage({ signInButtonClicked() }, e) }
            }
        }
    }

    private fun changeActivity() {
        saveUser()
    }

    private fun saveUser() {
        disposable.add(saveUserUseCase.execute(model,
                {
                    saveToken()
                },
                { e ->
                    ui().perform { it.showErrorMessage({ saveUser() }, e) }
                }
        ))

    }

    private fun saveToken() {
        disposable.add(saveTokenUseCase.execute(Token(model.token ?: ""),
                {
                    ui().perform { ui ->
                        ui.toMainActivity()
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ saveToken() }, e) }
                }
        ))
    }

    private fun validUser(): Boolean {
        return model.hasCorrectEmail()
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
        fun showErrorInfo(error: APIError.ErrorType)
        fun toggleSignInButton(isToggled: Boolean)
        fun showInfoTv(isVisible: Boolean)
        fun setErrorOnEmail()
        fun hideEmailError()
    }
}