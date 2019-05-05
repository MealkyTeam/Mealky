package com.teammealky.mealky.presentation.shoppinglist

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ClearShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.RemoveFromShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.UpdateShoppingListItemUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import timber.log.Timber
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
        private val shoppingListUseCase: ShoppingListUseCase,
        private val clearShoppingListUseCase: ClearShoppingListUseCase,
        private val addToShoppingListUseCase: AddToShoppingListUseCase,
        private val updateShoppingListItemUseCase: UpdateShoppingListItemUseCase,
        private val removeFromShoppingListUseCase: RemoveFromShoppingListUseCase
) : BasePresenter<ShoppingListPresenter.UI>() {

    private var models = mutableListOf<IngredientViewModel>()

    override fun attach(ui: UI) {
        super.attach(ui)

        if (models.isNotEmpty())
            setupInitialState(models)
        else {
            disposable.add(shoppingListUseCase.execute(
                    { ingredients ->

                        models = ingredients.map { item -> IngredientViewModel(item, false) }.toMutableList()
                        models = mergeDuplicates()

                        saveMergedToMemory()
                        setupInitialState(models)
                    },
                    { e ->
                        Timber.e("KUBA_LOG Method:attach ***** $e *****")
                    })
            )
        }
    }

    private fun mergeDuplicates(): MutableList<IngredientViewModel> {
        val groups = models.map {
            models.filter { current ->
                Ingredient.isSameIngredientWithDifferentQuantity(current.item, it.item)
            }
        }
        val distinctGroups = groups.distinct()

        val result = distinctGroups.map { group ->
            group.reduce { previousElement, currentElement ->
                val sum = previousElement.item.quantity + currentElement.item.quantity
                previousElement.copy(item = previousElement.item.copy(quantity = sum))
            }
        }

        return result.toMutableList()
    }

    private fun saveMergedToMemory() {
        clearMemory(onSuccess = { saveToMemory(models) })
    }

    private fun saveToMemory(models: MutableList<IngredientViewModel>) {
        disposable.add(addToShoppingListUseCase.execute(models.map { it.item },
                {},
                { e ->
                    Timber.e("KUBA_LOG Method:saveMergedToMemory ***** $e *****")
                }
        ))
    }

    private fun setupInitialState(models: List<IngredientViewModel>) {
        ui().perform {
            it.setupRecyclerView(models)

            val isEmpty = models.isEmpty()
            it.showEmptyView(isEmpty)
            it.enableClearListBtn(!isEmpty)
        }
    }

    fun onItemClicked(item: IngredientViewModel) {
        if (!item.isChecked)
            removeFromShoppingList(item)
        else
            addToShoppingList(item)
    }

    private fun removeFromShoppingList(model: IngredientViewModel) {
        val removedModel = models.firstOrNull { currentModel ->
            Ingredient.isSameIngredientWithDifferentQuantity(currentModel.item, model.item)
        } ?: return

        models.remove(removedModel)
        models.add(model.copy(isChecked = true))

        disposable.add(removeFromShoppingListUseCase.execute(model.item,
                {
                    ui().perform { ui ->
                        ui.fillList(models)
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ removeFromShoppingList(model) }, e) }
                })
        )
    }

    private fun addToShoppingList(model: IngredientViewModel) {
        val currentModel = models.firstOrNull { item -> item == model }
        currentModel?.let { viewModel ->
            viewModel.isChecked = false
            viewModel.item = model.item.copy(quantity = model.item.quantity)
        }

        getRemovedToBottom()

        disposable.add(addToShoppingListUseCase.execute(listOf(model.item),
                {
                    ui().perform {
                        ui().perform { ui -> ui.fillList(models) }
                    }
                },
                { e ->
                    ui().perform { it.showErrorMessage({ addToShoppingList(model) }, e) }
                })
        )
    }

    private fun getRemovedToBottom() {
        val removed = models.filter { item -> (item.isChecked) }
        models.removeAll(removed)
        models.addAll(removed)
    }

    fun onClearListBtnClicked() {
        ui().perform { it.showDialog() }
    }

    fun clearConfirmed() {
        clearMemory(onSuccess = { clearUI(it) })
    }

    private fun clearMemory(onSuccess: (Boolean) -> (Unit) = {}) {
        disposable.add(clearShoppingListUseCase.execute(
                { succeeded ->
                    onSuccess(succeeded)
                },
                { e ->
                    ui().perform { it.showErrorMessage({ clearConfirmed() }, e) }
                })
        )
    }

    private fun clearUI(succeeded: Boolean) {
        models.clear()
        ui().perform { ui ->
            ui.showToast(succeeded)
            ui.clearList()
            ui.enableClearListBtn(false)
            ui.showEmptyView(true)
            ui.fillList(models)
        }
    }

    fun fieldChanged(model: IngredientViewModel, quantity: Double) {
        if (model.isChecked) return

        var updatedModel = model
        var previousQuantity = 0.0

        val list = models.map {
            if (Ingredient.isSameIngredientWithDifferentQuantity(model.item, it.item)) {
                val updatedIngredient = model.item.copy(quantity = quantity)
                updatedModel = model.copy(item = updatedIngredient)
                previousQuantity = it.item.quantity
                return@map updatedModel
            } else
                return@map it
        }.toMutableList()

        models = list
        updateModel(updatedModel, previousQuantity)
    }

    private fun updateModel(updatedModel: IngredientViewModel, previousQuantity: Double) {
        models = models.map { currentModel ->
            return@map if (Ingredient.isSameIngredientWithDifferentQuantity(currentModel.item, updatedModel.item))
                updatedModel
            else
                currentModel
        }.toMutableList()

        disposable.add(updateShoppingListItemUseCase.execute(updatedModel.item,
                {},
                { e ->
                    ui().perform { it.showErrorMessage({ updateModel(updatedModel, previousQuantity) }, e) }
                })
        )
    }

    fun onInformationPassed(ingredient: Ingredient) {
        val model = IngredientViewModel(ingredient, false)
        models.add(model)

        ui().perform {
            it.showEmptyView(false)
            it.enableClearListBtn(true)
        }
        addToShoppingList(model)
    }

    fun onPlusBtnClicked() {
        ui().perform { it.showAddIngredientDialog(models.map { model -> model.item }) }
    }

    interface UI : BaseUI {
        fun setupRecyclerView(ingredients: List<IngredientViewModel>)
        fun showDialog()
        fun showToast(succeeded: Boolean)
        fun fillList(ingredients: List<IngredientViewModel>)
        fun clearList()
        fun enableClearListBtn(isEnabled: Boolean)
        fun showEmptyView(isEnabled: Boolean)
        fun showAddIngredientDialog(ingredients: List<Ingredient>)
    }
}