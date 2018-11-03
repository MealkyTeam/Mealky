package com.teammealky.mealky.presentation.settings

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
) : BasePresenter<SettingsPresenter.UI>() {

    interface UI : BaseUI {
    }
}