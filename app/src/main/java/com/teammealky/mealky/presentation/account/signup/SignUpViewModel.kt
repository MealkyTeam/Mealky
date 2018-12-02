package com.teammealky.mealky.presentation.account.signup

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(override val presenter: SignUpPresenter) :
        BaseViewModel<SignUpPresenter>(presenter)
