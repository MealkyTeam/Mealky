package com.teammealky.mealky.presentation.discover

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(
) : BasePresenter<DiscoverPresenter.UI>() {

    interface UI : BaseUI {
    }
}