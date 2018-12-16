package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.usecase.shoppinglist.ClearShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
        private val shoppingListUseCase: ShoppingListUseCase,
        private val clearShoppingListUseCase: ClearShoppingListUseCase
) : BasePresenter<ShoppingListPresenter.UI>() {

    private var ingredients = emptyList<Ingredient>()
    fun setupPresenter() {
        disposable.add(shoppingListUseCase.execute(
                { ingredients ->
                    this.ingredients = ingredients
                    ui().perform {
                        it.setupRecyclerView(ingredients)

                        val isEmpty = ingredients.isEmpty()
                        it.showEmptyView(isEmpty)
                        it.enableClearListBtn(!isEmpty)
                    }

                },
                { e ->
                    Timber.e("KUBA Method:onIngredientsButtonClicked ***** $e *****")
                })
        )
    }

    fun onItemClicked(item: Ingredient, checked: Boolean) {
        Timber.d("KUBA Method:onItemClicked *****  *****")
    }

    fun onClearListBtnClicked() {
        ui().perform {
            it.clearList()
            it.showSnackbar()
            it.enableClearListBtn(false)
            it.showEmptyView(true)
        }
    }

    fun onSnackbarActionClicked() {
        ui().perform {
            it.fillList(ingredients)
            it.showEmptyView(false)
            it.enableClearListBtn(true)
        }
    }

    fun snackbarDismissed() {
        disposable.add(clearShoppingListUseCase.execute(
                { succeeded ->
                    ingredients = emptyList()
                    ui().perform { ui ->
                        ui.showToast(succeeded)
                    }
                },
                { e ->
                    Timber.e("KUBA Method:onIngredientsButtonClicked ***** $e *****")
                })
        )
    }

    interface UI : BaseUI {
        fun setupRecyclerView(ingredients: List<Ingredient>)
        fun showSnackbar()
        fun showToast(succeeded: Boolean)
        fun fillList(ingredients: List<Ingredient>)
        fun clearList()
        fun enableClearListBtn(isEnabled: Boolean)
        fun showEmptyView(isEnabled: Boolean)
    }
}