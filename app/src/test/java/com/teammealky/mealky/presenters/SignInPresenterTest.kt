package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.signin.SignInWithPasswordUseCase
import com.teammealky.mealky.domain.usecase.token.SaveTokenUseCase
import com.teammealky.mealky.domain.usecase.user.SaveUserUseCase
import com.teammealky.mealky.presentation.account.signin.SignInPresenter
import org.junit.Before
import io.mockk.*
import io.reactivex.Single
import org.junit.Test

class SignInPresenterTest {

    private val authorizationRepository = mockk<AuthorizationRepository>()
    private val userRepository = mockk<UserRepository>()
    private val tokenRepository = mockk<TokenRepository>()

    private val mockSignInWithPasswordUseCase = spyk(SignInWithPasswordUseCase(authorizationRepository))
    private val mockSaveUserUseCase = spyk(SaveUserUseCase(userRepository))
    private val mockSaveTokenUseCase = spyk(SaveTokenUseCase(tokenRepository))

    private val view = mockk<SignInPresenter.UI>()

    private lateinit var presenter: SignInPresenter

    @Before
    fun setUp() {
        every { view.isLoading(any()) } just Runs
        every { view.toMainActivity() } just Runs
        every { view.toSignUpFragment() } just Runs
        every { view.toForgottenPasswordFragment() } just Runs
        every { view.showErrorInfo(any()) } just Runs
        every { view.toggleSignInButton(any()) } just Runs
        every { view.showInfoTv(any()) } just Runs
        every { view.setErrorOnEmail() } just Runs
        every { view.hideEmailError() } just Runs
        every { view.hideKeyboard() } just Runs

        presenter = SignInPresenter(mockSignInWithPasswordUseCase, mockSaveUserUseCase, mockSaveTokenUseCase)

        val user = MockDataTest.USERS[0]
        every {
            mockSignInWithPasswordUseCase.asSingle(
                    SignInWithPasswordUseCase.Params(user.email!!, user.password!!)
            )
        } returns Single.just(user)

        every {
            mockSaveUserUseCase.asSingle(user)
        } returns Single.just(true)

        every {
            mockSaveTokenUseCase.asSingle(Token(user.token!!))
        } returns Single.just(true)
    }

    /**
     * Scenario: User entered valid data to all fields and clicks sign in.
     * Given empty fields
     * When user enters valid data to both fields
     * And click enter
     * Then log him in
     */
    @Test
    fun `Sign in user`() {
        //When
        presenter.attach(view)
        presenter.model = MockDataTest.USERS[0]
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.hideKeyboard()
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.isLoading(false)
            view.toMainActivity()
        }
    }

    /**
     * Scenario: User entered invalid email to field and clicks sign in.
     * Given empty fields
     * When user enters invalid data to email
     * And click enter
     * Then show error
     */
    @Test
    fun `Try to sign in with invalid email`() {
        //When
        presenter.attach(view)
        presenter.model = MockDataTest.USERS[2]
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.setErrorOnEmail()
        }
    }
}