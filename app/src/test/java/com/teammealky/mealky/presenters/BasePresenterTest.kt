package com.teammealky.mealky.presenters

import com.teammealky.mealky.presentation.main.MainPresenter
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class BasePresenterTest {

    private lateinit var presenter: MainPresenter
    private val view = mockk<MainPresenter.UI>()

    @Before
    fun setUp() {
        presenter = MainPresenter()
        every { view.hideKeyboard() } just Runs
    }

    /**
     * Scenario: User clicks anywhere then hide keyboard
     * Given attached presenter
     * When user clicks anywhere
     * Then hide keyboard
     */
    @Test
    fun `User clicks anywhere then hide keyboard`() {
        //Given
        presenter.attach(view)

        //When
        presenter.touchEventDispatched()

        //Then
        verifySequence {
            view.hideKeyboard()
        }
    }
}