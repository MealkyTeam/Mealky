package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.model.Token
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.repository.TokenRepository
import com.teammealky.mealky.domain.repository.UserRepository
import com.teammealky.mealky.domain.usecase.signin.SignInWithTokenUseCase
import com.teammealky.mealky.domain.usecase.token.GetTokenUseCase
import com.teammealky.mealky.domain.usecase.user.SaveUserUseCase
import com.teammealky.mealky.presentation.splash.SplashPresenter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import io.mockk.*

class SplashPresenterTest {

    private val authorizationRepository = mockk<AuthorizationRepository>()
    private val userRepository = mockk<UserRepository>()
    private val tokenRepository = mockk<TokenRepository>()

    private val mockSignInWithTokenUseCase = spyk(SignInWithTokenUseCase(authorizationRepository))
    private val mockSaveUserUseCase = spyk(SaveUserUseCase(userRepository))
    private val mockGetTokenUseCase = spyk(GetTokenUseCase(tokenRepository))

    private val view = mockk<SplashPresenter.UI>()

    private lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        every { mockGetTokenUseCase.asSingle() } returns Single.just(MockDataTest.CORRECT_TOKEN)

        every { view.toMainActivity() } just Runs
        every { view.toSignIn() } just Runs

        presenter = SplashPresenter(mockSignInWithTokenUseCase, mockSaveUserUseCase, mockGetTokenUseCase)
    }

    /**
     * Scenario: Open app with correct saved user token
     * Given new created presenter
     * When I attach presenter
     * Then it will login and redirect to main activity.
     */
    @Test
    fun `Attach presenter with correct token`() {
        //Given
        every { mockSignInWithTokenUseCase.asSingle(SignInWithTokenUseCase.Params(MockDataTest.CORRECT_TOKEN.token)) } returns
                Single.just(MockDataTest.USERS[0])
        every { mockSaveUserUseCase.asSingle(MockDataTest.USERS[0]) } returns Single.just(true)

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.toMainActivity()
        }
    }

    /**
     * Scenario: Open app with correct wrong user token
     * Given new created presenter
     * When I attach presenter
     * Then it won't login and it will redirect to sign in fragment.
     */
    @Test
    fun `Attach presenter with wrong token`() {
        //Given
        every { mockSignInWithTokenUseCase.asSingle(SignInWithTokenUseCase.Params(MockDataTest.CORRECT_TOKEN.token)) } returns
                Single.error(Throwable("Some error"))

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.toSignIn()
        }
    }

    /**
     * Scenario: Open app without saved user token
     * Given new created presenter
     * When I attach presenter
     * Then it won't login and it will redirect to sign in fragment.
     */
    @Test
    fun `Attach presenter without token`() {
        //Given
        every { mockGetTokenUseCase.asSingle() } returns Single.just(Token.emptyToken())

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.toSignIn()
        }
    }
}