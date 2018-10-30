package com.kuba.mealky.presentation.meal

import com.kuba.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MealViewModel @Inject constructor(override val presenter: MealPresenter) :
        BaseViewModel<MealPresenter>(presenter)
