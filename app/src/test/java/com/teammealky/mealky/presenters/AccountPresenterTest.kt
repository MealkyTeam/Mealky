package com.teammealky.mealky.presenters

import com.teammealky.mealky.presentation.account.AccountPresenter
import com.teammealky.mealky.presentation.account.signin.SignInFragment
import org.junit.Before
import org.junit.Test
import io.mockk.*

class AccountPresenterTest {

    private val view = mockk<AccountPresenter.UI>()

    private lateinit var presenter: AccountPresenter

    @Before
    fun setUp() {
        every { view.toSignIn() } just Runs

        presenter = AccountPresenter()
    }

    /**
     * Scenario: Attach presenter to activity
     * Given new created presenter
     * When I attach presenter
     * Then show sign in fragment.
     */
    @Test
    fun `Attach presenter with correct token`() {
        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.toSignIn()
        }
    }
}