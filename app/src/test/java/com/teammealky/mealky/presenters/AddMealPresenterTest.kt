package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest.Companion.THUMBNAIL_IMAGE
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class AddMealPresenterTest {

    private val view = mockk<AddMealPresenter.UI>()
    private lateinit var presenter: AddMealPresenter

    @Before
    fun setUp() {
        presenter = AddMealPresenter()

        every { view.enableConfirmBtn(any()) } just Runs
        every { view.toMealsFragment() } just Runs
        every { view.showErrors(any()) } just Runs
        every { view.showToast() } just Runs
        every { view.clearErrors() } just Runs
        every { view.isLoading(any()) } just Runs
        every { view.showGalleryCameraDialog() } just Runs
        every { view.showAddIngredientDialog(any()) } just Runs
        every { view.showImagesQueue(any()) } just Runs
        every { view.enableImagesBtn(any()) } just Runs
        every { view.setupAdapter(any()) } just Runs
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
        presenter.confirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.showErrors(listOf(TITLE_ERROR))
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
        presenter.confirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.showErrors(listOf(PREP_ERROR))
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
        presenter.confirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.showErrors(listOf(PREP_TIME_ERROR))
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
        presenter.confirmBtnClicked()

        //Then
        verifySequence {
            view.showImagesQueue(mutableListOf())
            view.enableImagesBtn(true)
            view.setupAdapter(emptyList())
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.showErrors(listOf(PREP_ERROR, PREP_TIME_ERROR))
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
        val newThumbnailPath = "newThumbnailPath"
        val newThumbnail = ThumbnailImage(5, newThumbnailPath)

        //When
        presenter.onInformationPassed(newThumbnailPath)

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
                THUMBNAIL_IMAGE.copy(id = 3), THUMBNAIL_IMAGE.copy(id = 4),THUMBNAIL_IMAGE.copy(id = 4))
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
}