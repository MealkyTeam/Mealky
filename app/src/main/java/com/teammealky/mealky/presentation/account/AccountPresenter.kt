package com.teammealky.mealky.presentation.account

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AccountPresenter @Inject constructor() : BasePresenter<AccountPresenter.UI>() {

    interface UI : BaseUI
}