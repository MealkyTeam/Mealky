package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ClearShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.RemoveFromShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
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
        disposable.add(removeFromShoppingListUseCase.execute(model.item,
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
        val updatedModel = setQuantityIfZero(model)
        disposable.add(addToShoppingListUseCase.execute(listOf(updatedModel.item),
                {
                    val currentModel = models.firstOrNull { item -> item.position == model.position }
                    currentModel?.let {
                        model.isGreyedOut = false
                        model.item = model.item.copy(quantity = updatedModel.item.quantity)
                    }

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

    private fun setQuantityIfZero(model: ShoppingListItemViewModel): ShoppingListItemViewModel {
        if (model.item.quantity != 0.0) return model

        val updatedIngredient = model.item.copy(quantity = 1.0)
        return model.copy(item = updatedIngredient)
    }

    fun onClearListBtnClicked() {
        ui().perform { it.showDialog() }
    }

    fun clearConfirmed() {
        models = emptyList()
        disposable.add(clearShoppingListUseCase.execute(
                { succeeded ->
                    ui().perform { ui ->
                        ui.showToast(succeeded)
                        ui.clearList()
                        ui.enableClearListBtn(false)
                        ui.showEmptyView(true)
                        ui.fillList(models)
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ clearConfirmed() }, e) }
                })
        )
    }

    fun fieldChanged(model: ShoppingListItemViewModel, text: String) {
        if (model.isGreyedOut) return

        var updatedModel = model
        var previousQuantity = 0.0

        val mutableList = models.map {
            if (Ingredient.isSameIngredientWithDifferentQuantity(model.item, it.item)) {
                val updatedIngredient = model.item.copy(quantity = text.toDouble())
                updatedModel = model.copy(item = updatedIngredient)
                previousQuantity = it.item.quantity
                return@map updatedModel
            } else
                return@map it
        }

        if (text.toDouble() == 0.0)
            removeFromShoppingList(updatedModel)
        else
            updateModel(updatedModel, previousQuantity)


        models = mutableList

    }

    private fun updateModel(updatedModel: ShoppingListItemViewModel, previousQuantity: Double) {
        val ingredient = updatedModel.item.copy(quantity = updatedModel.item.quantity - previousQuantity)
        disposable.add(addToShoppingListUseCase.execute(listOf(ingredient),
                {},
                { e ->
                    ui().perform { it.showErrorMessage({ updateModel(updatedModel, previousQuantity) }, e) }
                })
        )
    }

    fun addIngredient(ingredient: Ingredient) {
        val position = getNextPosition()
        val model = ShoppingListItemViewModel(ingredient, false, position)
        models += model
        ui().perform {
            it.showEmptyView(false)
            it.enableClearListBtn(true)
        }
        addToShoppingList(model)
    }

    private fun getNextPosition(): Int {
        return (models.maxBy { it.position }?.position ?: 0) + 1
    }

    fun onPlusBtnClicked() {
        ui().perform { it.showAddIngredientDialog() }
    }

    interface UI : BaseUI {
        fun setupRecyclerView(ingredients: List<ShoppingListItemViewModel>)
        fun showDialog()
        fun showToast(succeeded: Boolean)
        fun fillList(ingredients: List<ShoppingListItemViewModel>)
        fun clearList()
        fun enableClearListBtn(isEnabled: Boolean)
        fun showEmptyView(isEnabled: Boolean)
        fun showAddIngredientDialog()
    }
}