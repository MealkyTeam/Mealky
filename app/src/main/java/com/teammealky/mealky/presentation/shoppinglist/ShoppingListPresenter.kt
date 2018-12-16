package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
        private val shoppingListUseCase: ShoppingListUseCase
) : BasePresenter<ShoppingListPresenter.UI>() {

    fun setupPresenter(){
        disposable.add(shoppingListUseCase.execute(
                { ingredients ->
                    Timber.d("KUBA Method:setupPresenter *****  *****")
                    for(i in ingredients)
                        Timber.d("KUBA Method:setupPresenter ***** $i *****")
                },
                { e ->
                    Timber.e("KUBA Method:onIngredientsButtonClicked ***** $e *****")
                })
        )
    }

    interface UI : BaseUI {
    }
}