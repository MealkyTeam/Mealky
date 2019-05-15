package com.teammealky.mealky.presenters

import android.os.Bundle
import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.MockDataTest.Companion.NOT_EMPTY_QUERY_WITHOUT_RESULT
import com.teammealky.mealky.MockDataTest.Companion.NOT_EMPTY_QUERY_WITH_RESULT
import com.teammealky.mealky.data.repository.MealsDataRepository
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.meals.MealListPresenter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MealListPresenterTest {

    private val mockRepository = mockk<MealsDataRepository>()
    private val mockUseCase = spyk(ListMealsUseCase(mockRepository))

    private val view = mockk<MealListPresenter.UI>()

    private lateinit var presenter: MealListPresenter

    @Before
    fun setUp() {
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE)
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8, true)) } returns Single.just(MockDataTest.MEALS_PAGE)
        every { mockUseCase.asSingle(ListMealsUseCase.Params(NOT_EMPTY_QUERY_WITH_RESULT, 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE2)
        every { mockUseCase.asSingle(ListMealsUseCase.Params(NOT_EMPTY_QUERY_WITHOUT_RESULT, 0, 8)) } returns Single.just(MockDataTest.MEALS_EMPTY_PAGE)

        every { view.setupRecyclerView() } just Runs
        every { view.openItem(any()) } just Runs
        every { view.isLoading(any()) } just Runs
        every { view.fillList(any()) } just Runs
        every { view.scrollToSaved(any()) } just Runs
        every { view.showErrorMessage(any(), any()) } just Runs
        every { view.showErrorMessage(any(), any(), false) } just Runs
        every { view.showEmptyView(any(), any()) } just Runs
        every { view.clearList() } just Runs
        every { view.hideKeyboard() } just Runs
        every { view.openAddMeal() } just Runs
        every { view.scrollToTop(any()) } just Runs
        every { view.clearSearchText() } just Runs
        every { view.stopSpinner() } just Runs
        every { mockRepository.invalidate() } just Runs

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
        //Given
        presenter.attach(view)

        //When
        presenter.firstRequest()

        //Then
        verifySequence {
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)
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
        presenter.firstRequest()

        //Then
        verifySequence {
            //loadMore
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //firstRequest
            view.clearList()
            view.fillList(MockDataTest.MEALS)
            view.scrollToSaved(any())
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
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, MealListPresenter.LIMIT)) } returns Single.error(error)

        //When
        presenter.attach(view)
        presenter.firstRequest()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.showErrorMessage(any(), error, false)
        }
    }

    /**
     * Scenario: Show error on meals load more fetch fail
     * Given there is fail in connection
     * When I attach presenter
     * And I will scroll down
     * Then do nothing
     */
    @Test
    fun `Do nothing if something went wrong on load more`() {
        //Given
        val error = Exception("Some error occurred")

        //When
        presenter.attach(view)
        presenter.firstRequest()
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.error(error)
        presenter.loadMore()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //loadMore
            view.isLoading(true)
            view.isLoading(false)
        }
    }

    /**
     * Scenario: User enters data to search and api returns meals
     * Given there is presenter attached
     * When I enter data to search
     * Then display fetched meals
     */
    @Test
    fun `Search which returns some data`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.currentQuery = NOT_EMPTY_QUERY_WITH_RESULT
        presenter.search()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //search
            view.isLoading(true)
            view.clearList()
            view.scrollToTop()

            //loadMore
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS.reversed())
            view.isLoading(false)
            view.showEmptyView(false, NOT_EMPTY_QUERY_WITH_RESULT)
        }
    }

    /**
     * Scenario: User enters data to search and api returns empty page
     * Given there is presenter attached
     * When I enter data to search
     * Then display empty item
     */
    @Test
    fun `Search which returns empty page`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.currentQuery = NOT_EMPTY_QUERY_WITHOUT_RESULT
        presenter.search()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //search
            view.isLoading(true)
            view.clearList()
            view.scrollToTop()

            //loadMore
            view.isLoading(true)
            view.fillList(emptyList())
            view.isLoading(false)
            view.showEmptyView(true, NOT_EMPTY_QUERY_WITHOUT_RESULT)
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
        presenter.firstRequest()

        //When
        presenter.onItemClicked(MockDataTest.MEALS.first())

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //onItemClick
            view.openItem(MockDataTest.MEALS.first())
        }
    }

    /**
     * Scenario: On plus button click
     * Given there is filled presenter
     * When I click on plus button
     * Then it will show new fragment
     */
    @Test
    fun `Open add meal fragment`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.onAddMealBtnClicked()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //onAddMealBtnClicked
            view.openAddMeal()
        }
    }

    /**
     * Scenario: Check if should stop load more and it should
     * Given there is filled presenter
     * When I scroll to bottom
     * Then presenter must check if it should stop load more and it should return yes
     */
    @Test
    fun `Check if should stop load more and it should`() {
        //Given
        presenter.attach(view)
        presenter.currentQuery = NOT_EMPTY_QUERY_WITHOUT_RESULT
        presenter.isLast = true

        //When
        //scrolled

        //Then
        assertEquals(true, presenter.shouldStopLoadMore())
    }

    /**
     * Scenario: Check if should stop load more and it shouldn't
     * Given there is filled presenter
     * When I scroll to bottom
     * Then presenter must check if it should stop load more and it should return no
     */
    @Test
    fun `Check if should stop load more and it shouldn't`() {
        //Given
        presenter.attach(view)
        presenter.currentQuery = NOT_EMPTY_QUERY_WITHOUT_RESULT
        presenter.isLast = false

        //When
        //scrolled

        //Then
        assertEquals(false, presenter.shouldStopLoadMore())
        presenter.currentQuery = ""
        presenter.isLast = true
        assertEquals(false, presenter.shouldStopLoadMore())
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
        val savedPosition = Bundle()
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.onPaused(savedPosition)
        presenter.firstRequest()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false)

            //firstRequest
            view.clearList()
            view.fillList(MockDataTest.MEALS)
            view.scrollToSaved(savedPosition)
        }
    }

    /**
     * Scenario: Scroll to top on tab reselect
     * Given there is filled presenter
     * When user clicks on meal list tab
     * Then smoothly scroll to top
     */
    @Test
    fun `Scroll to top on tab reselect`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.fragmentReselected()

        //Then
        verifyOrder {
            view.scrollToTop(true)
        }
    }

    /**
     * Scenario: Invalidate repository
     * Given there is filled presenter
     * When old meals data should be refetched
     * Then clear everything and fetch first page again
     */
    @Test
    fun `Invalidate repository`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE2)

        //When
        presenter.invalidateList(true)

        //Then
        verifyOrder {
            //search
            view.clearSearchText()
            view.isLoading(true)
            view.clearList()
            view.scrollToTop()
            view.isLoading(true)

            //loadMore
            view.fillList(MockDataTest.MEALS_PAGE2.items)
            view.isLoading(false)
            view.showEmptyView(false, "")
        }
    }

    /**
     * Scenario: Refresh after user swiped down
     * Given there is filled presenter
     * When user swipes down
     * Then clear everything and fetch again
     */
    @Test
    fun `Refresh after user swiped down`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE2)

        //When
        presenter.swipedContainer()

        //Then
        verifyOrder {
            //swipedContainer
            view.stopSpinner()

            //search
            view.isLoading(true)
            view.clearList()
            view.scrollToTop()

            //loadMore
            view.fillList(MockDataTest.MEALS_PAGE2.items)
            view.isLoading(false)
            view.showEmptyView(false, "")
        }
    }
}