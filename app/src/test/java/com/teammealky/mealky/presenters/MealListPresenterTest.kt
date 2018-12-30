package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.data.repository.MealsDataRepository
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.meals.MealListPresenter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import io.mockk.*

class MealListPresenterTest {

    private val mockRepository = mockk<MealsDataRepository>()
    private val mockUseCase = spyk(ListMealsUseCase(mockRepository))

    private val view = mockk<MealListPresenter.UI>()

    private lateinit var presenter: MealListPresenter

    @Before
    fun setUp() {
        every { mockUseCase.asSingle(ListMealsUseCase.Params(-1, 0, 8)) } returns Single.just(MockDataTest.PAGE)
        every { view.setupRecyclerView() } just Runs
        every { view.openItem(any()) } just Runs
        every { view.isLoading(any()) } just Runs
        every { view.fillList(any()) } just Runs
        every { view.setVisibleItem(any()) } just Runs
        every { view.showErrorMessage(any(),any()) } just Runs

        presenter = MealListPresenter(mockUseCase)
    }

    /**
     * Scenario: Filling list with new data
     * Given new created presenter
     * When I attach presenter
     * Then it will fetch data and fill list with it.
     */
    @Test
    fun `Attach presenter if empty`() {
        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
        }
    }

    /**
     * Scenario: Filling list with already fetched data
     * Given not empty presenter
     * When I attach presenter
     * Then it fill list with already fetched data and scroll to saved item.
     */
    @Test
    fun `Attach presenter if NOT empty`() {
        //Given
        presenter.loadMore()

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            //loadMore
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)

            //attach
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.setVisibleItem(any())
            view.isLoading(false)
        }
    }

    /**
     * Scenario: Show error on meals first fetch fail
     * Given there is fail in connection
     * When I attach presenter
     * Then it will show error.
     */
    @Test
    fun `Show error if something went wrong on first fetch`() {
        //Given
        val error = Exception("Some error occurred")
        every { mockUseCase.asSingle(ListMealsUseCase.Params(-1, 0, MealListPresenter.LIMIT)) } returns Single.error(error)

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            //attach
            view.isLoading(true)
            view.showErrorMessage(any(), error)
        }
    }

    /**
     * Scenario: Show error on meals load more fetch fail
     * Given there is fail in connection
     * When I attach presenter
     * And I will scroll down
     * Then it will show error.
     */
    @Test
    fun `Show error if something went wrong on load more`() {
        //Given
        val error = Exception("Some error occurred")

        //When
        presenter.attach(view)
        every { mockUseCase.asSingle(ListMealsUseCase.Params(-1, 0, 8)) } returns Single.error(error)
        presenter.loadMore()

        //Then
        verifySequence {
            //attach
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)

            //loadMore
            view.isLoading(true)
            view.showErrorMessage(any(), error)
        }
    }

    /**
     * Scenario: On item click
     * Given there is filled presenter
     * When I click on item
     * Then it will show new fragment
     */
    @Test
    fun `Open meal details`() {
        //Given
        presenter.attach(view)

        //When
        presenter.onItemClicked(MockDataTest.MEALS.first())

        //Then
        verifySequence {
            //attach
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)

            //onItemClick
            view.openItem(MockDataTest.MEALS.first())
        }
    }

    /**
     * Scenario: Pause fragment
     * Given there is filled presenter
     * When I change fragment or pause the app
     * And when I go back to fragment
     * Then I will set visible item correctly
     */
    @Test
    fun `Save item position`() {
        //Given
        presenter.attach(view)

        //When
        presenter.onPaused(2)
        presenter.attach(view)

        //Then
        verifySequence {
            //attach
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)

            //attach
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.setVisibleItem(2)
            view.isLoading(false)
        }
    }
}