package com.teammealky.mealky.presenters

import androidx.recyclerview.widget.RecyclerView
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

class MealListPresenterTest {

    private val mockRepository = mockk<MealsDataRepository>()
    private val mockUseCase = spyk(ListMealsUseCase(mockRepository))

    private val view = mockk<MealListPresenter.UI>()

    private lateinit var presenter: MealListPresenter

    @Before
    fun setUp() {
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE)
        every { mockUseCase.asSingle(ListMealsUseCase.Params(NOT_EMPTY_QUERY_WITH_RESULT, 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE2)
        every { mockUseCase.asSingle(ListMealsUseCase.Params(NOT_EMPTY_QUERY_WITHOUT_RESULT, 0, 8)) } returns Single.just(MockDataTest.MEALS_EMPTY_PAGE)

        every { view.setupRecyclerView() } just Runs
        every { view.openItem(any()) } just Runs
        every { view.isLoading(any()) } just Runs
        every { view.fillList(any()) } just Runs
        every { view.setVisibleItem(any()) } just Runs
        every { view.showErrorMessage(any(), any()) } just Runs
        every { view.showErrorMessage(any(), any(), false) } just Runs
        every { view.showEmptyView(any(), any()) } just Runs
        every { view.clearList() } just Runs
        every { view.scrollToTop() } just Runs
        every { view.hideKeyboard() } just Runs
        every { view.openAddMeal() } just Runs

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
            view.showEmptyView(false, "")
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
            view.showEmptyView(false, "")

            //firstRequest
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
            view.showEmptyView(false, "")

            //loadMore
            view.isLoading(true)
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
            view.showEmptyView(false, "")

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
            view.showEmptyView(false, "")

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
            view.showEmptyView(false, "")

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
            view.showEmptyView(false, "")

            //onAddMealBtnClicked
            view.openAddMeal()
        }
    }

    /**
     * Scenario: Hide keyboard on scroll state changed to drag
     * Given there is filled presenter
     * When I try to scroll list
     * Then it will hide keyboard
     */
    @Test
    fun `Hide keyboard on list dragged`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.scrolled(RecyclerView.SCROLL_STATE_DRAGGING)

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false, "")

            //onItemClick
            view.hideKeyboard()
        }
    }

    /**
     * Scenario: Don't hide keyboard on scroll state changed from drag
     * Given there is filled presenter
     * When I stop scrolling
     * Then do nothing
     */
    @Test
    fun `Do nothing on different scroll state`() {
        //Given
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.scrolled(RecyclerView.SCROLL_STATE_IDLE)

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false, "")
        }
    }

    /**
     * Scenario: Check if is last page and it is
     * Given there is filled presenter
     * When I scroll to bottom
     * Then presenter must check if is last page and say yes
     */
    @Test
    fun `Check if last page (YES)`() {
        //Given
        presenter.attach(view)
        presenter.currentQuery = NOT_EMPTY_QUERY_WITHOUT_RESULT

        //When
        //scrolled

        //Then
        assertEquals(true, presenter.shouldStopLoadMore())
    }

    /**
     * Scenario: Check if is last page and there is more pages left
     * Given there is filled presenter
     * When I scroll to bottom
     * Then presenter must check if is last page and say nop
     */
    @Test
    fun `Check if last page (NO)`() {
        //Given
        every { mockUseCase.asSingle(ListMealsUseCase.Params("", 0, 8)) } returns Single.just(MockDataTest.MEALS_PAGE2)
        presenter.attach(view)

        //When
        //scrolled

        //Then
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
        presenter.attach(view)
        presenter.firstRequest()

        //When
        presenter.onPaused(2)
        presenter.attach(view)
        presenter.firstRequest()

        //Then
        verifySequence {
            //firstRequest
            view.isLoading(true)
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.isLoading(false)
            view.showEmptyView(false, "")

            //firstRequest
            view.isLoading(true)
            view.fillList(MockDataTest.MEALS)
            view.setVisibleItem(2)
            view.isLoading(false)
        }
    }
}