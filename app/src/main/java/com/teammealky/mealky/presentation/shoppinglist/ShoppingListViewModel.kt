package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.presentation.commons.presenter.BaseViewModel
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(override val presenter: ShoppingListPresenter) :
        BaseViewModel<ShoppingListPresenter>(presenter)
