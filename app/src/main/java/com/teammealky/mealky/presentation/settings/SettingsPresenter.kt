package com.teammealky.mealky.presentation.settings

import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.token.ClearTokenUseCase
import com.teammealky.mealky.domain.usecase.user.ClearUserUseCase
import com.teammealky.mealky.domain.usecase.user.GetUserUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val clearUserUseCase: ClearUserUseCase,
        private val clearTokeUseCase: ClearTokenUseCase,
        private val getUserUseCase: GetUserUseCase
) : BasePresenter<SettingsPresenter.UI>() {

    private fun clearUserData() {
        disposable.add(clearUserUseCase.execute(
                {
                    clearToken()
                },
                { e ->
                    ui().perform { it.showErrorMessage({ clearUserData() }, e) }
                }
        ))
    }

    private fun clearToken() {
        disposable.add(clearTokeUseCase.execute(
                {
                },
                { e ->
                    ui().perform { it.showErrorMessage({ clearToken() }, e) }
                }
        ))
    }

    fun signOutClicked() {
        clearUserData()
        ui().perform { it.toAccountActivity() }
    }

    fun setupPresenter() {
        disposable.add(getUserUseCase.execute(
                { user ->
                    ui().perform { it.setupView(user) }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ setupPresenter() }, e) }
                }
        ))
    }

    interface SignOutListener {
        fun signOutBtnClicked()
    }

    interface UI : BaseUI {
        fun toAccountActivity()
        fun setupView(user: User)
    }
}