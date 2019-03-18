package com.teammealky.mealky.presenters

import com.teammealky.mealky.presentation.addmeal.AddMealPresenter
import com.teammealky.mealky.presentation.addmeal.AddMealPresenter.ValidationResult.*
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
            view.enableConfirmBtn(false)
            view.clearErrors()
            view.isLoading(true)
            view.showErrors(listOf(PREP_ERROR, PREP_TIME_ERROR))
        }
    }
}