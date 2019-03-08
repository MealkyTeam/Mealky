package com.teammealky.mealky.presentation.addmeal

import android.text.Editable
import com.teammealky.mealky.presentation.addmeal.model.MealViewModel
import com.teammealky.mealky.presentation.commons.extension.toInt
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class AddMealPresenter @Inject constructor() : BasePresenter<AddMealPresenter.UI>() {

    var model: MealViewModel = MealViewModel.basicMealViewModel()

    fun fieldsChanged(title: Editable?, preparationTime: Editable?, description: Editable?) {
        val titleString = title?.toString() ?: ""
        val preparationTimeString = (preparationTime?.toInt() ?: 0).toString()
        val descriptionString = description?.toString() ?: ""

        model = MealViewModel(titleString, preparationTimeString, descriptionString)

        ui().perform { it.enableConfirmBtn(fieldsAreNoteEmpty()) }
    }

    private fun fieldsAreNoteEmpty() = model.description.trim().isNotEmpty() && model.title.trim().isNotEmpty()
            && model.preparationTime.trim().isNotEmpty()

    fun touchedOutside() {
        ui().perform { it.hideKeyboard() }
    }

    fun confirmBtnClicked() {
        if (fieldsValidated()) {
            ui().perform {
                it.showToast()
                it.toMealsFragment()
            }
        } else {
            ui().perform { it.showErrors() }

        }
    }

    private fun fieldsValidated(): Boolean {
        //todo
        return true
    }

    fun addImagesBtnClicked() {
        Timber.tag("KUBA").v("addImagesBtnClicked")
    }

    fun addIngredientsBtnClicked() {
        Timber.tag("KUBA").v("addIngredientsBtnClicked ")
    }

    interface UI : BaseUI {
        fun enableConfirmBtn(isEnabled: Boolean)
        fun toMealsFragment()
        fun showErrors()
        fun showToast()
    }
}