package com.teammealky.mealky.presentation.account

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signin.SignInWithTokenUseCase
import com.teammealky.mealky.domain.usecase.token.GetTokenUseCase
import com.teammealky.mealky.domain.usecase.user.SaveUserUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class AccountPresenter @Inject constructor(
        private val signInWithTokenUseCase: SignInWithTokenUseCase,
        private val saveUserUseCase: SaveUserUseCase,
        private val getTokenUseCase: GetTokenUseCase
) : BasePresenter<AccountPresenter.UI>() {

    fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>? = null) {
        ui().perform { it.setContent(fragment, cleanStack, transitionValues) }
    }

    fun validateToken() {
        disposable.add(getTokenUseCase.execute(
                { token ->
                    if (token == Token.emptyToken())
                        ui().perform { it.toSignIn() }
                    else
                        sendToken(token.token)
                },
                { e ->
                    Timber.e("KUBA Method:sendToken ***** ERROR: $e *****")
                    ui().perform { it.toSignIn() }
                })
        )
    }

    private fun sendToken(token: String) {
        disposable.add(signInWithTokenUseCase.execute(
                SignInWithTokenUseCase.Params(token),
                { user ->
                    saveUser(user)
                },
                { e ->
                    Timber.e("KUBA Method:sendToken ***** ERROR: $e *****")
                    ui().perform { it.toSignIn() }
                })
        )
    }

    private fun saveUser(user: User) {
        disposable.add(saveUserUseCase.execute(
                user, {},
                { e ->
                    Timber.e("KUBA Method:saveUser ***** $e *****")
                })
        )

        ui().perform {
            it.toMainActivity()
        }
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
        fun toSignIn()
        fun toMainActivity()
    }
}