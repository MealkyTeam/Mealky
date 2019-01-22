package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.model.APIError
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.usecase.resetPassword.ResetPasswordUseCase
import com.teammealky.mealky.presentation.account.forgottenPassword.ForgottenPasswordPresenter
import org.junit.Before
import io.mockk.*
import io.reactivex.Single
import org.junit.Test

class ForgottenPasswordPresenterTest {
    private val mockRepository = mockk<AuthorizationRepository>()
    private val mockUseCase = spyk(ResetPasswordUseCase(mockRepository))

    private val view = mockk<ForgottenPasswordPresenter.UI>()

    private lateinit var presenter: ForgottenPasswordPresenter

    @Before
    fun setUp() {
        every { view.isLoading(any()) } just Runs
        every { view.showErrorInfo(any()) } just Runs
        every { view.showInfoTv(any()) } just Runs
        every { view.setErrorOnEmail() } just Runs
        every { view.hideEmailError() } just Runs
        every { view.showToast() } just Runs
        every { view.toSignInFragment() } just Runs
        every { view.toggleResetPasswordButton(any()) } just Runs

        presenter = ForgottenPasswordPresenter(mockUseCase)

        val user = MockDataTest.USERS[0]
        every {
            mockUseCase.asSingle(user.email ?: "")
        } returns Single.just(true)
    }

    /**
     * Scenario: User entered valid email and clicks reset password button.
     * Given empty fields
     * When user enters valid data
     * And click enter
     * Then change fragment and show toast
     */
    @Test
    fun `Reset password`() {
        //When
        presenter.attach(view)
        presenter.model = MockDataTest.USERS[0]
        presenter.fieldsChanged()

        presenter.onResetBtnClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleResetPasswordButton(true)

            //onResetBtnClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.isLoading(false)
            view.showToast()
            view.toSignInFragment()
        }
    }

    /**
     * Scenario: User entered invalid email and clicks reset password.
     * Given empty field
     * When user enters invalid email
     * And click enter
     * Then show error
     */
    @Test
    fun `Try to reset password with invalid email`() {
        //When
        presenter.attach(view)
        presenter.model = MockDataTest.USERS[2]
        presenter.fieldsChanged()

        presenter.onResetBtnClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleResetPasswordButton(true)

            //onResetBtnClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.setErrorOnEmail()
        }
    }

    /**
     * Scenario: User entered unregistered email and clicks reset password.
     * Given empty field
     * When user enters invalid email
     * And click enter
     * Then show error
     */
    @Test
    fun `Try to reset password with unregistered email`() {
        //Given
        val user = MockDataTest.USERS[0]
        every {
            mockUseCase.asSingle(user.email!!)
        } returns Single.error(APIError(APIError.NO_SUCH_USER))

        //When
        presenter.attach(view)
        presenter.model = user
        presenter.fieldsChanged()

        presenter.onResetBtnClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleResetPasswordButton(true)

            //onResetBtnClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.showInfoTv(true)
            view.isLoading(false)
            view.showErrorInfo(APIError.ErrorType.NO_SUCH_USER)
        }
    }

    /**
     * Scenario: User clicks on sign in link
     * Given attached presenter
     * When user clicks on sign in link
     * Then go to sign up fragment
     */
    @Test
    fun `Click sign in link`() {
        //Given
        presenter.attach(view)

        //When
        presenter.signInLinkClicked()

        //Then
        verifySequence {
            //signInLinkClicked
            view.toSignInFragment()
        }
    }
}