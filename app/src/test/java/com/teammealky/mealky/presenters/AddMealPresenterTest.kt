package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.MockDataTest.Companion.THUMBNAIL_IMAGE
import com.teammealky.mealky.domain.repository.AddMealRepository
import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.addmeal.AddMealUseCase
import com.teammealky.mealky.domain.usecase.user.GetUserUseCase
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.io.File

class AddMealPresenterTest {
    private val addMealRepository = mockk<AddMealRepository>()
    private val userRepository = mockk<UserRepository>()

    private val addMealUseCase = spyk(AddMealUseCase(addMealRepository))
    private val getUsersUseCase = spyk(GetUserUseCase(userRepository))

    private val view = mockk<AddMealPresenter.UI>()
    private lateinit var presenter: AddMealPresenter

    @Before
    fun setUp() {
        presenter = AddMealPresenter(addMealUseCase, getUsersUseCase)

        every { view.enableConfirmBtn(any()) } just Runs
        every { view.toMainActivity() } just Runs
        every { view.showErrors(any()) } just Runs
        every { view.showToast() } just Runs
        every { view.clearErrors() } just Runs
        every { view.isLoading(any()) } just Runs
        every { view.showGalleryCameraDialog() } just Runs
        every { view.showAddIngredientDialog(any()) } just Runs
        every { view.showImagesQueue(any()) } just Runs
        every { view.enableImagesBtn(any()) } just Runs
        every { view.setupAdapter(any()) } just Runs
        every { view.updateIngredients(any()) } just Runs
        every { view.hideKeyboard() } just Runs
        every { view.forceGoBack() } just Runs
        every { view.showGoBackDialog() } just Runs
        every { view.showErrorMessage(any(),any()) } just Runs
    }

    /**
     * Scenario: Enable button when every field will be filled
     * Given new created presenter
     * When I attach presenter
     * And user fills all field
     * Then enable button.
     */
    @Test
    fun `Enable button when every field will be filled`() {
        //Given
        val title = "Title"
        val description = "Description"
        val prepTime = "30"

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(true)
        }
    }

    /**
     * Scenario: Disable button when not every field will be filled
     * Given new created presenter
     * When I attach presenter
     * And user fills all field
     * Then disable button.
     */
    @Test
    fun `Disable button when not every field will be filled`() {
        //Given
        val title = "Title"
        val description = ""
        val prepTime = "30"

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
        }
    }

    /**
     * Scenario: Show error on title
     * Given new created presenter
     * When I attach presenter
     * And user enters wrong data
     * Then show error on title.
     */
    @Test
    fun `Show error on title`() {
        //Given
        val title = ""
        val description = "Description"
        val prepTime = "30"

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)
        presenter.onConfirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(TITLE_ERROR, IMAGES_ERROR, INGREDIENTS_ERROR))
        }
    }

    /**
     * Scenario: Show error on description
     * Given new created presenter
     * When I attach presenter
     * And user enters wrong data
     * Then show error on description.
     */
    @Test
    fun `Show error on description`() {
        //Given
        val title = "Title"
        val description = ""
        val prepTime = "30"

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)
        presenter.onConfirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(PREP_ERROR, IMAGES_ERROR, INGREDIENTS_ERROR))
        }
    }

    /**
     * Scenario: Show error on prep time
     * Given new created presenter
     * When I attach presenter
     * And user enters wrong data
     * Then show error on prep time.
     */
    @Test
    fun `Show error on prep time`() {
        //Given
        val title = "Title"
        val description = "Description"
        val prepTime = ""

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)
        presenter.onConfirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(PREP_TIME_ERROR, IMAGES_ERROR, INGREDIENTS_ERROR))
        }
    }

    /**
     * Scenario: Show error on prep time and description
     * Given new created presenter
     * When I attach presenter
     * And user enters wrong data
     * Then show error on prep time and description.
     */
    @Test
    fun `Show error on prep time and description`() {
        //Given
        val title = "Title"
        val description = ""
        val prepTime = "-10"

        //When
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)
        presenter.onConfirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(PREP_ERROR, PREP_TIME_ERROR, IMAGES_ERROR, INGREDIENTS_ERROR))
        }
    }

    /**
     * Scenario: Disable add images button when 5 images has been added
     * Given presenter with 4 attachments
     * When I add one more
     * Then disable add images button
     */
    @Test
    fun `Disable add images button`() {
        //Given
        presenter.attach(view)
        val queue = mutableListOf(THUMBNAIL_IMAGE.copy(id = 1),
                THUMBNAIL_IMAGE.copy(id = 2), THUMBNAIL_IMAGE.copy(id = 3), THUMBNAIL_IMAGE.copy(id = 4))
        val queueCopy = queue.toMutableList()
        presenter.attachments = queue
        val newThumbnailFile = File("newThumbnailPath")
        val newThumbnail = ThumbnailImage(5, newThumbnailFile)

        //When
        presenter.onInformationPassed(newThumbnailFile)

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())

            view.showImagesQueue((queueCopy + newThumbnail) as MutableList)
            view.enableImagesBtn(false)
        }
    }

    /**
     * Scenario: Enable add images button when there is less than 5 images
     * Given presenter with 5 attachments
     * When I remove one
     * Then enable add images button
     */
    @Test
    fun `Enable add images button`() {
        //Given
        val queue = mutableListOf(THUMBNAIL_IMAGE.copy(id = 1), THUMBNAIL_IMAGE.copy(id = 2),
                THUMBNAIL_IMAGE.copy(id = 3), THUMBNAIL_IMAGE.copy(id = 4), THUMBNAIL_IMAGE.copy(id = 4))
        presenter.attachments = queue
        presenter.attach(view)
        val queueWithFour = queue.toMutableList()
        queueWithFour.remove(queue.last())

        //When
        presenter.onImageDeleteClicked(queue.last())

        //Then
        verifySequence {
            view.showImagesQueue(queue)
            view.enableImagesBtn(false)
            view.setupAdapter(emptyList())

            view.showImagesQueue(queueWithFour)
            view.enableImagesBtn(true)
        }
    }

    /**
     * Scenario: Show dialog on ingredient button clicked
     * Given attached presenter
     * When user clicks on ingredient button
     * Then show add ingredient dialog
     */
    @Test
    fun `Show dialog on ingredient button clicked`() {
        //Given
        presenter.attach(view)

        //When
        presenter.addIngredientsBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())

            view.showAddIngredientDialog(emptyList())
        }
    }

    /**
     * Scenario: Add ingredient from dialog to list
     * Given attached presenter with empty ingredient list
     * When finish add ingredient dialog
     * Then add now ingredient to list
     */
    @Test
    fun `Add ingredient from dialog to list`() {
        //Given
        presenter.attach(view)

        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
        }

        val ingredient = MockDataTest.INGREDIENTS[0]
        val models = listOf(IngredientViewModel(ingredient.copy(), false))

        //When
        presenter.onInformationPassed(ingredient)

        //Then
        verifyOrder {
            view.updateIngredients(models)
        }
    }

    /**
     * Scenario: Remove ingredient on user click on remove button
     * Given attached presenter with some ingredients
     * When user clicks on remove ingredient button
     * Then remove that ingredient from list
     */
    @Test
    fun `Remove ingredient on user click on remove button`() {
        //Given
        val models = generateIngredientMocks()
        val middleItem = models[1]
        presenter.ingredientModels = models
        presenter.attach(view)
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(models)
        }

        //When
        presenter.onIngredientDeleteClicked(middleItem)

        //Then
        verifyOrder {
            view.setupAdapter(models.minusElement(middleItem))
        }
    }

    /**
     * Scenario: Update presenter's list of ingredients when ingredient quantity is changed
     * Given attached presenter with some ingredients
     * When user changes quantity
     * Then update list of ingredients
     */
    @Test
    fun `Update presenter's list of ingredients when ingredient quantity is changed`() {
        //Given
        val initialModels = generateIngredientMocks()
        val resultModels = initialModels.mapIndexed { index, model ->
            return@mapIndexed if (index == 1)
                model.copy(item = model.item.copy(quantity = 25.0))
            else
                model
        }

        val middleItem = initialModels[1]
        presenter.ingredientModels = initialModels
        presenter.attach(view)
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(initialModels)
        }

        //When
        presenter.onIngredientChanged(middleItem, 25.0)

        //Then
        assert(presenter.ingredientModels == resultModels)
    }

    /**
     * Scenario: Show error on images
     * Given filled all text fields and added at least one ingredient
     * When user clicks on confirm button
     * Then show error on images.
     */
    @Test
    fun `Show error on images`() {
        //Given
        val title = "Test"
        val description = "Description"
        val prepTime = "30"
        presenter.ingredientModels = generateIngredientMocks()
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)

        //When
        presenter.onConfirmBtnClicked()

        //Then
        verifyOrder {
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(IMAGES_ERROR))
        }
    }

    /**
     * Scenario: Show error on ingredients
     * Given filled all text fields and added at least one image
     * When user clicks on confirm button
     * Then show error on ingredients.
     */
    @Test
    fun `Show error on ingredients`() {
        //Given
        val title = "Test"
        val description = "Description"
        val prepTime = "30"
        presenter.attachments = generateThumbnailMocks()
        presenter.attach(view)
        presenter.fieldsChanged(title, prepTime, description)

        //When
        presenter.onConfirmBtnClicked()

        //Then
        verifyOrder {
            view.clearErrors()
            view.isLoading(true)
            view.isLoading(false)
            view.showErrors(listOf(INGREDIENTS_ERROR))
        }
    }

    /**
     * Scenario: Show dialog on go back
     * Given attached presenter
     * When user clicks on go back
     * Then show dialog.
     */
    @Test
    fun `Show dialog on go back`() {
        //Given
        presenter.attach(view)

        //When
        presenter.onBackPressed()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())

            view.showGoBackDialog()
        }
    }

    /**
     * Scenario: Go back if is confirmed
     * Given attached presenter with displayed dialog
     * When user clicks on confirm button
     * Then go back
     */
    @Test
    fun `Go back if is confirmed`() {
        //Given
        presenter.attach(view)

        //When
        presenter.goBackConfirmed()

        //Then`
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())

            view.forceGoBack()
        }
    }

    /**
     * Scenario: Send meal if everything is filled correctly
     * Given attached presenter with all data filled correctly
     * When user clicks on confirm button
     * Then display toast and go to main activity
     */
    @Test
    fun `Send meal if everything is filled correctly`() {
        //Given
        val title = "Title"
        val description = "preparation"
        val prepTime = "30"
        val user = MockDataTest.USERS[0]
        presenter.attach(view)
        presenter.attachments = generateThumbnailMocks()
        presenter.ingredientModels = generateIngredientMocks()
        presenter.fieldsChanged(title, prepTime, description)

        every {
            getUsersUseCase.asSingle()
        } returns Single.just(user)

        every {
            addMealUseCase.asSingle(generateMockAddMealParams())
        } returns Single.just(true)

        //When
        presenter.onConfirmBtnClicked()

        //Then`
        verifyOrder {
            view.clearErrors()
            view.isLoading(true)

            view.isLoading(false)
            view.showToast()
            view.toMainActivity()
        }
    }

    /**
     * Scenario: Send meal and something went wrong
     * Given attached presenter with all data filled correctly
     * When user clicks on confirm button
     * Then display error that some error from api occurred
     */
    @Test
    fun `Send meal and something went wrong`() {
        //Given
        val title = "Title"
        val description = "preparation"
        val prepTime = "30"
        val user = MockDataTest.USERS[0]
        presenter.attach(view)
        presenter.attachments = generateThumbnailMocks()
        presenter.ingredientModels = generateIngredientMocks()
        presenter.fieldsChanged(title, prepTime, description)
        val error = Exception("Meal should contain at least one ingredient.")


        every {
            getUsersUseCase.asSingle()
        } returns Single.just(user)

        every {
            addMealUseCase.asSingle(generateMockAddMealParams())
        } returns Single.error(error)

        //When
        presenter.onConfirmBtnClicked()

        //Then`
        verifyOrder {
            view.clearErrors()
            view.isLoading(true)

            view.showErrorMessage(any(), error)
        }
    }

    private fun generateIngredientMocks() =
            MockDataTest.INGREDIENTS.map { IngredientViewModel(it, false) }.toMutableList()

    private fun generateThumbnailMocks() =
            mutableListOf(ThumbnailImage(1, File("urlToImage")))

    private fun generateMockAddMealParams() = AddMealUseCase.Params(
            "Title", 30,
            "preparation", MockDataTest.USERS[0], MockDataTest.INGREDIENTS, generateFilesMocks()
    )

    private fun generateFilesMocks() = listOf(File("urlToImage"))
}