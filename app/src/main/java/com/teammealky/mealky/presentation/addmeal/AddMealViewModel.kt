package com.teammealky.mealky.presentation.addmeal

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class AddMealViewModel @Inject constructor(override val presenter: AddMealPresenter) :
        BaseViewModel<AddMealPresenter>(presenter)
