package com.kuba.mealky.presentation.meal

import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.presentation.commons.presenter.BasePresenter
import com.kuba.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject


class MealPresenter @Inject constructor(
) : BasePresenter<MealPresenter.UI>() {

    var meal: Meal? = null
    interface UI : BaseUI {
    }
}