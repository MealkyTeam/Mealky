package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.R.string.meals
import com.teammealky.mealky.data.repository.MealsDataRepository
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.meals.MealListPresenter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import io.mockk.*

class MealListPresenterTest {

    private var mockRepository = mockk<MealsDataRepository>()
    private var mockUseCase = spyk(ListMealsUseCase(mockRepository))

    private var view = mockk<MealListPresenter.UI>()

    private lateinit var presenter: MealListPresenter

    @Before
    fun setUp() {
        every { mockUseCase.asSingle(ListMealsUseCase.Params(-1, 0, 8)) } returns Single.just(MockDataTest.PAGE)
        every { view.isLoading(any())} just Runs
        every { view.fillList(any())} just Runs
        every { view.setVisibleItem(any())} just Runs
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
}