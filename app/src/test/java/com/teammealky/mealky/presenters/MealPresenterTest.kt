package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.presentation.meal.MealPresenter
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import org.junit.Before
import io.mockk.*
import io.reactivex.Single
import org.junit.Test

class MealPresenterTest {

    private val shoppingListRepository = mockk<ShoppingListRepository>()
    private val addToShoppingListUseCase = spyk(AddToShoppingListUseCase(shoppingListRepository))
    private val view = mockk<MealPresenter.UI>()
    private lateinit var presenter: MealPresenter

    private lateinit var meal: Meal
    private lateinit var models: List<IngredientViewModel>

    @Before
    fun setUp() {
        meal = MockDataTest.MEALS[0]
        models = meal.ingredients.map { IngredientViewModel(it, false) }
        presenter = MealPresenter(addToShoppingListUseCase)

        every { view.showToast(any()) } just Runs
        every { view.enableButton(any()) } just Runs
        every { view.setupView(any(), any()) } just Runs
        every { view.showErrorMessage(any(), any()) } just Runs
    }

    /**
     * Scenario: Open meal and correctly fill it
     * Given new created presenter
     * When presenter is attached
     * Then field will fill with correct data.
     */
    @Test
    fun `Open meal and correctly fill it`() {
        //Given

        //When
        presenter.onCreated(meal)
        presenter.attach(view)

        //Then
        verifySequence {
            view.setupView(meal, models)
            view.enableButton(false)
        }
    }

    /**
     * Scenario: Ingredient was clicked
     * Given presenter with filled data
     * When user click on ingredient
     * Then update list of models and enable button.
     * When user click on ingredient again
     * Then update list of models and disable button.
     */
    @Test
    fun `Ingredient was clicked (first)`() {
        //Given
        presenter.onCreated(meal)
        presenter.attach(view)

        //When
        presenter.onIngredientClicked(models[1])
        presenter.onIngredientClicked(models[1])

        //Then
        verifyOrder {
            view.enableButton(true)
            view.enableButton(false)
        }
    }


    /**
     * Scenario: Save ingredients and show toast on button click
     * Given presenter with filled data
     * When user clicks on button
     * Then Save ingredients and show toast.
     */
    @Test
    fun `Save ingredients and show toast on button click`() {
        //Given
        presenter.onCreated(meal)
        presenter.attach(view)
        every { addToShoppingListUseCase.asSingle(models.map { it.item }) } returns Single.just(true)

        //When
        presenter.onIngredientClicked(models[1])
        presenter.onIngredientClicked(models[0])
        presenter.onIngredientsButtonClicked()

        //Then
        verifyOrder {
            //attach
            view.setupView(meal, models)
            view.enableButton(false)

            //onIngredientClicked x2
            view.enableButton(true)
            view.enableButton(true)

            view.showToast(true)
        }
    }

    /**
     * Scenario: Show error if something went wrong during saving ingredients
     * Given presenter with filled data
     * When user clicks on button
     * Then Show error.
     */
    @Test
    fun `Show error if something went wrong during saving ingredients`() {
        //Given
        presenter.onCreated(meal)
        presenter.attach(view)
        val errorMsg = APIError.SOMETHING_WENT_WRONG
        val error = Throwable(errorMsg)

        every { addToShoppingListUseCase.asSingle(models.map { it.item }) } returns Single.error(error)

        //When
        presenter.onIngredientClicked(models[1])
        presenter.onIngredientClicked(models[0])
        presenter.onIngredientsButtonClicked()

        //Then
        verify {
            view.showErrorMessage(any(), error)
        }
    }
}