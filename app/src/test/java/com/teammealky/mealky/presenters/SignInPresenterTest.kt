package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.model.APIError
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
        every { view.showErrorMessage(any(), any()) } just Runs

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
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.isLoading(false)
            view.toMainActivity()
        }
    }

    /**
     * Scenario: User entered invalid email and clicks sign in.
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

    /**
     * Scenario: User entered invalid password and clicks sign in.
     * Given empty fields
     * When user enters invalid password
     * And click enter
     * Then show api error
     */
    @Test
    fun `Try to sign in with invalid password`() {
        //Given
        val user = MockDataTest.USERS[0]
        every {
            mockSignInWithPasswordUseCase.asSingle(
                    SignInWithPasswordUseCase.Params(user.email!!, user.password!!)
            )
        } returns Single.error(APIError(APIError.WRONG_PASSWORD))

        //When
        presenter.attach(view)
        presenter.model = user
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.showInfoTv(true)
            view.isLoading(false)
            view.showErrorInfo(APIError.ErrorType.WRONG_PASSWORD)
        }
    }

    /**
     * Scenario: User entered email that is not in database and clicks sign in.
     * Given empty fields
     * When user enters invalid email
     * And click enter
     * Then show api error
     */
    @Test
    fun `Try to sign in with email that is not in database`() {
        //Given
        val user = MockDataTest.USERS[0]
        every {
            mockSignInWithPasswordUseCase.asSingle(
                    SignInWithPasswordUseCase.Params(user.email!!, user.password!!)
            )
        } returns Single.error(APIError(APIError.NO_SUCH_USER))

        //When
        presenter.attach(view)
        presenter.model = user
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.showInfoTv(true)
            view.isLoading(false)
            view.showErrorInfo(APIError.ErrorType.NO_SUCH_USER)
        }
    }

    /**
     * Scenario: User entered email that is not confirmed and clicks sign in.
     * Given empty fields
     * When user enters invalid email
     * And click enter
     * Then show api error
     */
    @Test
    fun `Try to sign in with email that is not confirmed`() {
        //Given
        val user = MockDataTest.USERS[0]
        every {
            mockSignInWithPasswordUseCase.asSingle(
                    SignInWithPasswordUseCase.Params(user.email!!, user.password!!)
            )
        } returns Single.error(APIError(APIError.CONFIRM_EMAIL))


        //When
        presenter.attach(view)
        presenter.model = user
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.showInfoTv(true)
            view.isLoading(false)
            view.showErrorInfo(APIError.ErrorType.CONFIRM_EMAIL)
        }
    }

    /**
     * Scenario: User entered valid data but something goes wrong.
     * Given empty fields
     * When user enters correct data
     * And click enter
     * Then show api error
     */
    @Test
    fun `Try to sign in with correct data but something goes wrong`() {
        //Given
        val user = MockDataTest.USERS[0]
        val error = APIError("Weird error")
        every {
            mockSignInWithPasswordUseCase.asSingle(
                    SignInWithPasswordUseCase.Params(user.email!!, user.password!!)
            )
        } returns Single.error(error)


        //When
        presenter.attach(view)
        presenter.model = user
        presenter.fieldsChanged()

        presenter.signInButtonClicked()

        //Then
        verifySequence {
            //fieldsChanged
            view.toggleSignInButton(true)

            //signInButtonClicked
            view.hideEmailError()
            view.showInfoTv(false)
            view.isLoading(true)

            view.showInfoTv(true)
            view.isLoading(false)
            view.showErrorMessage(any(), error)
        }
    }

    /**
     * Scenario: User clicks on forgotten password link
     * Given attached presenter
     * When user clicks on forgotten password link
     * Then go to forgotten password fragment
     */
    @Test
    fun `Click forgotten password link`() {
        //Given
        presenter.attach(view)

        //When
        presenter.forgottenPasswordLinkClicked()

        //Then
        verifySequence {
            //forgottenPasswordLinkClicked
            view.toForgottenPasswordFragment()
        }
    }

    /**
     * Scenario: User clicks on sign up link
     * Given attached presenter
     * When user clicks on sign up link
     * Then go to sign up fragment
     */
    @Test
    fun `Click sign up link`() {
        //Given
        presenter.attach(view)

        //When
        presenter.signUpLinkClicked()

        //Then
        verifySequence {
            //forgottenPasswordLinkClicked
            view.toSignUpFragment()
        }
    }
}