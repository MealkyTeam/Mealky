package com.kuba.mealky.presentation.meals

import com.kuba.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MealListViewModel @Inject constructor(override val presenter: MealListPresenter) :
        BaseViewModel<MealListPresenter>(presenter)
