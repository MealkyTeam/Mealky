package com.teammealky.mealky.presentation.account

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(override val presenter: AccountPresenter) :
        BaseViewModel<AccountPresenter>(presenter)
