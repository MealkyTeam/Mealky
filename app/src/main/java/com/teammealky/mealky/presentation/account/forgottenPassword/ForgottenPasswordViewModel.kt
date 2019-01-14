package com.teammealky.mealky.presentation.account.forgottenPassword

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class ForgottenPasswordViewModel @Inject constructor(override val presenter: ForgottenPasswordPresenter) :
        BaseViewModel<ForgottenPasswordPresenter>(presenter)
