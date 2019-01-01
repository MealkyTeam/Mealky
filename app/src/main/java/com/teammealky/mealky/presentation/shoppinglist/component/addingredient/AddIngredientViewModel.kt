package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class AddIngredientViewModel @Inject constructor(
        override val presenter: AddIngredientPresenter
) : BaseViewModel<AddIngredientPresenter>(presenter)
