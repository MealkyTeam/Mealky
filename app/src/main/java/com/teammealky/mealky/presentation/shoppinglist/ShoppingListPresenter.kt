package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
) : BasePresenter<ShoppingListPresenter.UI>() {

    interface UI : BaseUI {
    }
}