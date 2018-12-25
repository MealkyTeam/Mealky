package com.teammealky.mealky.presentation.shoppinglist.component.addingredient

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class AddIngredientPresenter @Inject constructor() : BasePresenter<AddIngredientPresenter.UI>() {


    interface UI : BaseUI {
    }
}