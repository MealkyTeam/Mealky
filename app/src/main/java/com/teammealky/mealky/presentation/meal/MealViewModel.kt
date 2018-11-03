package com.teammealky.mealky.presentation.meal

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MealViewModel @Inject constructor(override val presenter: MealPresenter) :
        BaseViewModel<MealPresenter>(presenter)
