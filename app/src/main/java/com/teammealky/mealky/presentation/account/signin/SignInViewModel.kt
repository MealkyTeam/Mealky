package com.teammealky.mealky.presentation.account.signin

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class SignInViewModel @Inject constructor(override val presenter: SignInPresenter) :
        BaseViewModel<SignInPresenter>(presenter)
