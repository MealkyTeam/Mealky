package com.teammealky.mealky.presentation.meal

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject


class MealPresenter @Inject constructor(
) : BasePresenter<MealPresenter.UI>() {

    var meal: Meal? = null
    interface UI : BaseUI {
    }
}