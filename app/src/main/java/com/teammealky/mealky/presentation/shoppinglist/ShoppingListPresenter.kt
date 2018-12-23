package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ClearShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.RemoveFromShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import timber.log.Timber
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
        private val shoppingListUseCase: ShoppingListUseCase,
        private val clearShoppingListUseCase: ClearShoppingListUseCase,
        private val addToShoppingListUseCase: AddToShoppingListUseCase,
        private val removeFromShoppingListUseCase: RemoveFromShoppingListUseCase
) : BasePresenter<ShoppingListPresenter.UI>() {

    private var models = emptyList<ShoppingListItemViewModel>()
    fun setupPresenter() {
        disposable.add(shoppingListUseCase.execute(
                { ingredients ->
                    var position = 1
                    this.models = ingredients.map { item -> ShoppingListItemViewModel(item, false, position++) }
                    ui().perform {
                        it.setupRecyclerView(models)

                        val isEmpty = ingredients.isEmpty()
                        it.showEmptyView(isEmpty)
                        it.enableClearListBtn(!isEmpty)
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ setupPresenter() }, e) }
                })
        )
    }

    fun onItemClicked(item: ShoppingListItemViewModel, checked: Boolean) {
        if (checked)
            removeFromShoppingList(item)
        else
            addToShoppingList(item)
    }

    private fun removeFromShoppingList(model: ShoppingListItemViewModel) {
        disposable.add(removeFromShoppingListUseCase.execute(model.item.id,
                {

                    val removedModel = models.first { item -> item.position == model.position }

                    models -= listOf(removedModel)
                    removedModel.isGreyedOut = true
                    models += listOf(removedModel)

                    ui().perform { ui ->
                        ui.fillList(models)
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ removeFromShoppingList(model) }, e) }
                })
        )
    }

    private fun addToShoppingList(model: ShoppingListItemViewModel) {
        disposable.add(addToShoppingListUseCase.execute(listOf(model.item),
                {

                    models.first { item -> item.position == model.position }.isGreyedOut = false

                    val removed = models.filter { item -> (item.isGreyedOut) }
                    models -= removed
                    models += removed

                    ui().perform {
                        ui().perform { ui -> ui.fillList(models) }
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ addToShoppingList(model) }, e) }
                })
        )
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
            it.fillList(models)
            it.showEmptyView(false)
            it.enableClearListBtn(true)
        }
    }

    fun snackbarDismissed() {
        disposable.add(clearShoppingListUseCase.execute(
                { succeeded ->
                    models = emptyList()
                    ui().perform { ui ->
                        ui.showToast(succeeded)
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ snackbarDismissed() }, e) }
                })
        )
    }

    fun fieldChanged(model: ShoppingListItemViewModel, text: String) {
        Timber.d("KUBA Method:fieldChanged *****  *****")
    }

    interface UI : BaseUI {
        fun setupRecyclerView(ingredients: List<ShoppingListItemViewModel>)
        fun showSnackbar()
        fun showToast(succeeded: Boolean)
        fun fillList(ingredients: List<ShoppingListItemViewModel>)
        fun clearList()
        fun enableClearListBtn(isEnabled: Boolean)
        fun showEmptyView(isEnabled: Boolean)
    }
}