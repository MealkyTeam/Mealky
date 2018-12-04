package com.teammealky.mealky.presentation.account

import android.transition.TransitionValues
import androidx.fragment.app.Fragment
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.signin.SignInWithTokenUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class AccountPresenter @Inject constructor(private val signInWithTokenUseCase: SignInWithTokenUseCase
) : BasePresenter<AccountPresenter.UI>() {

    fun setContent(fragment: Fragment, cleanStack: Boolean, transitionValues: List<TransitionValues>? = null) {
        ui().perform { it.setContent(fragment, cleanStack, transitionValues) }
    }

    fun validateToken(token: String) {
        if (token.isEmpty())
            ui().perform { it.toSignIn() }
        else
            sendToken(token)
    }

    private fun sendToken(token: String) {
        disposable.add(signInWithTokenUseCase.execute(
                SignInWithTokenUseCase.Params(token),
                { user ->
                    ui().perform {
                        it.saveUsername(user)
                        it.toMainActivity()
                    }
                },
                { e ->
                    Timber.d("KUBA Method:sendToken ***** ERROR: $e *****")
                    ui().perform { it.toSignIn() }
                })
        )
    }

    interface UI : BaseUI {
        fun setContent(fragment: Fragment, cleanStack: Boolean = false, transitionValues: List<TransitionValues>? = null)
        fun toSignIn()
        fun toMainActivity()
        fun saveUsername(user: User)
    }
}