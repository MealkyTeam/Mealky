package com.teammealky.mealky.presentation.addmeal

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.usecase.addmeal.AddMealUseCase
import com.teammealky.mealky.domain.usecase.user.GetUserUseCase
import com.teammealky.mealky.presentation.addmeal.model.MealViewModel
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import java.io.File
import javax.inject.Inject

class AddMealPresenter @Inject constructor(
        private val addMealUseCase: AddMealUseCase,
        private val getUserUseCase: GetUserUseCase
) : BasePresenter<AddMealPresenter.UI>() {

    var model: MealViewModel = MealViewModel.basicMealViewModel()
    var attachments = mutableListOf<ThumbnailImage>()
    var ingredientModels = mutableListOf<IngredientViewModel>()
    var canGoBack = false

    override fun attach(ui: UI) {
        super.attach(ui)

        ui().perform {
            it.showImagesQueue(attachments)
            it.enableImagesBtn(!areAttachmentsFull())
            it.setupAdapter(ingredientModels)
        }
    }

    private fun areAttachmentsFull() = attachments.size >= MAX_NUMBER_OF_ATTACHMENTS

    fun fieldsChanged(title: String?, preparationTime: String?, description: String?) {
        val titleString = title ?: ""
        val preparationTimeString = preparationTime?.toIntOrNull()?.toString() ?: ""
        val descriptionString = description ?: ""

        model = MealViewModel(titleString, preparationTimeString, descriptionString)

        ui().perform { it.enableConfirmBtn(fieldsAreNoteEmpty()) }
    }

    private fun fieldsAreNoteEmpty() = model.description.trim().isNotEmpty() && model.title.trim().isNotEmpty()
            && model.preparationTime.trim().isNotEmpty()

    fun touchedOutside() {
        ui().perform { it.hideKeyboard() }
    }

    fun onConfirmBtnClicked() {
        ui().perform {
            it.clearErrors()
            it.isLoading(true)
        }
        val result = fieldsValidated()
        if (allAreCorrect(result)) {
            getUser { user -> sendMeal(user) }
        } else {
            ui().perform {
                it.isLoading(false)
                it.showErrors(result)
            }
        }
    }

    private fun getUser(onComplete: (User) -> Unit) {
        disposable.add(getUserUseCase.execute(
                {
                    onComplete(it)
                },
                { e ->
                    ui().perform {
                        it.isLoading(false)
                        it.showErrorMessage({
                            getUser { user -> sendMeal(user) }
                        }, e)
                    }
                }
        ))
    }

    private fun sendMeal(user: User) {
        val params = AddMealUseCase.Params(
                model.title,
                model.preparationTime.toIntOrNull() ?: 0,
                model.description,
                user,
                ingredientModels.map { it.item },
                attachments.map { it.file }
        )

        disposable.add(addMealUseCase.execute(params,
                {
                    ui().perform {
                        it.isLoading(false)
                        it.showToast()
                        it.toMainActivity()
                    }
                },
                { e ->
                    ui().perform {
                        it.isLoading(false)
                        it.showErrorMessage({
                            getUser { user -> sendMeal(user) }
                        }, e)
                    }
                }
        ))
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
        if (model.preparationTime.isEmpty() || model.preparationTime.toInt() <= 0)
            errors.add(PREP_TIME_ERROR)

        if (attachments.isEmpty())
            errors.add(IMAGES_ERROR)

        if (ingredientModels.isEmpty())
            errors.add(INGREDIENTS_ERROR)

        if (errors.isEmpty())
            errors.add(CORRECT)

        return errors
    }

    fun addImagesBtnClicked() {
        ui().perform { it.showGalleryCameraDialog() }
    }

    fun addIngredientsBtnClicked() {
        ui().perform { it.showAddIngredientDialog(ingredientModels.map { model -> model.item }) }
    }

    fun onInformationPassed(imagePath: File) {
        attachments.add(ThumbnailImage(getNewId(), imagePath))
        ui().perform {
            it.showImagesQueue(attachments)
            it.enableImagesBtn(!areAttachmentsFull())
        }
    }

    private fun getNewId() = ((attachments.maxBy { it.id }?.id) ?: attachments.size) + 1

    fun onInformationPassed(ingredient: Ingredient) {
        ingredientModels.add(IngredientViewModel(ingredient, false))

        ui().perform {
            it.updateIngredients(ingredientModels)
            it.hideKeyboard()
        }
    }

    fun onImageDeleteClicked(image: ThumbnailImage) {
        attachments.remove(image)
        ui().perform {
            it.showImagesQueue(attachments)
            it.enableImagesBtn(!areAttachmentsFull())
        }
    }

    fun onIngredientDeleteClicked(model: IngredientViewModel) {
        ingredientModels.remove(model)
        ui().perform { it.setupAdapter(ingredientModels) }
    }

    fun onIngredientChanged(model: IngredientViewModel, quantity: Double) {
        val list = ingredientModels.map {
            if (Ingredient.isSameIngredientWithDifferentQuantity(model.item, it.item)) {
                val updatedIngredient = model.item.copy(quantity = quantity)
                return@map model.copy(item = updatedIngredient)
            } else
                return@map it
        }.toMutableList()

        ingredientModels = list
    }

    fun onBackPressed() {
        if (!canGoBack)
            ui().perform { it.showGoBackDialog() }
    }

    fun goBackConfirmed() {
        canGoBack = true
        ui().perform { it.forceGoBack() }
    }

    interface UI : BaseUI {
        fun enableConfirmBtn(isEnabled: Boolean)
        fun toMainActivity()
        fun showErrors(errors: List<ValidationResult>)
        fun showToast()
        fun clearErrors()
        fun isLoading(isLoading: Boolean)
        fun showGalleryCameraDialog()
        fun showGoBackDialog()
        fun forceGoBack()
        fun showAddIngredientDialog(ingredients: List<Ingredient>)
        fun showImagesQueue(attachments: MutableList<ThumbnailImage>)
        fun enableImagesBtn(isEnabled: Boolean)
        fun updateIngredients(ingredients: List<IngredientViewModel>)
        fun setupAdapter(ingredients: List<IngredientViewModel>)
    }

    enum class ValidationResult {
        TITLE_ERROR,
        PREP_ERROR,
        PREP_TIME_ERROR,
        INGREDIENTS_ERROR,
        IMAGES_ERROR,
        CORRECT
    }

    companion object {
        const val MAX_NUMBER_OF_ATTACHMENTS = 5
    }
}