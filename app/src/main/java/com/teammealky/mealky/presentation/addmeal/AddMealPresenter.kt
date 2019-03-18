package com.teammealky.mealky.presentation.addmeal

import android.text.Editable
import com.teammealky.mealky.presentation.addmeal.model.MealViewModel
import com.teammealky.mealky.presentation.commons.extension.toInt
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import javax.inject.Inject
import timber.log.Timber

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
        ui().perform {
            it.clearErrors()
            it.isLoading(true)
        }
        val result = fieldsValidated()
        if (allAreCorrect(result)) {
            //todo send request to api
            ui().perform {
                it.showToast()
                it.toMealsFragment()
            }
        } else {
            ui().perform { it.showErrors(result) }
        }
    }

    private fun allAreCorrect(result: List<ValidationResult>): Boolean {
        return result.all { it == CORRECT }
    }

    private fun fieldsValidated(): List<ValidationResult> {
        val errors = mutableListOf<ValidationResult>()
        if (model.title.isEmpty())
            errors.add(TITLE_ERROR)
        if (model.description.isEmpty())
            errors.add(PREP_ERROR)
        if (model.preparationTime.isEmpty())
            errors.add(PREP_TIME_ERROR)

        if (errors.isEmpty())
            errors.add(CORRECT)

        return errors
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
        fun showErrors(errors: List<ValidationResult>)
        fun showToast()
        fun clearErrors()
        fun isLoading(isLoading: Boolean)
    }

    enum class ValidationResult {
        TITLE_ERROR,
        PREP_ERROR,
        PREP_TIME_ERROR,
        INGREDIENTS_ERROR,
        IMAGES_ERROR,
        CORRECT
    }
}