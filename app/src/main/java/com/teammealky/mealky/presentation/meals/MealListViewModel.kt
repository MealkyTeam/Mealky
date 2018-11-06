package com.teammealky.mealky.presentation.meals

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class MealListViewModel @Inject constructor(override val presenter: MealListPresenter) :
        BaseViewModel<MealListPresenter>(presenter)
