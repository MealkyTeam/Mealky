package com.teammealky.mealky.presentation.account.forgottenPassword

import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.resetpassword.ResetPasswordUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class ForgottenPasswordPresenter @Inject constructor(
        private val resetPasswordUseCase: ResetPasswordUseCase
) : BasePresenter<ForgottenPasswordPresenter.UI>() {

    var model = User.defaultUser()

    private fun validUser(): Boolean {
        return model.hasCorrectEmail()
    }

    fun onResetBtnClicked() {
        ui().perform {
            it.hideEmailError()
            it.showInfoTv(false)
        }

        if (!validUser()) {
            ui().perform { it.setErrorOnEmail() }
            return
        }

        ui().perform {
            it.isLoading(true)
        }

        disposable.add(resetPasswordUseCase.execute(
                model.email ?: "",
                {
                    ui().perform { ui ->
                        ui.isLoading(false)
                        ui.showToast()
                        ui.toSignInFragment()
                    }
                },
                { e ->
                    ui().perform {
                        it.showInfoTv(true)
                        it.isLoading(false)
                    }

                    if (e is APIError) {
                        when (e.type) {
                            APIError.ErrorType.NO_SUCH_USER -> {
                                ui().perform { it.showErrorInfo(APIError.ErrorType.NO_SUCH_USER) }
                            }
                            else -> {
                                ui().perform { it.showErrorMessage({ onResetBtnClicked() }, e) }
                            }
                        }
                    } else {
                        ui().perform { it.showErrorMessage({ onResetBtnClicked() }, e) }
                    }
                }
        ))
    }

    fun signInLinkClicked() {
        ui().perform { it.toSignInFragment() }
    }

    fun fieldsChanged() {
        if (model.email.isNullOrBlank())
            ui().perform { it.toggleResetPasswordButton(false) }
        else
            ui().perform { it.toggleResetPasswordButton(true) }
    }

    interface UI : BaseUI {
        fun isLoading(isLoading: Boolean)
        fun toggleResetPasswordButton(isToggled: Boolean)
        fun toSignInFragment()
        fun setErrorOnEmail()
        fun showInfoTv(isVisible: Boolean)
        fun hideEmailError()
        fun showToast()
        fun showErrorInfo(error: APIError.ErrorType)
    }
}