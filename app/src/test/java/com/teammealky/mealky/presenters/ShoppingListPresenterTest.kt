package com.teammealky.mealky.presenters

import com.teammealky.mealky.MockDataTest
import com.teammealky.mealky.domain.repository.ShoppingListRepository
import com.teammealky.mealky.domain.usecase.shoppinglist.AddToShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ClearShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.RemoveFromShoppingListUseCase
import com.teammealky.mealky.domain.usecase.shoppinglist.ShoppingListUseCase
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListPresenter
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ShoppingListPresenterTest {

    private val mockRepository = mockk<ShoppingListRepository>()
    private val view = mockk<ShoppingListPresenter.UI>()

    private val shoppingListUseCase = spyk(ShoppingListUseCase(mockRepository))
    private val clearShoppingListUseCase = spyk(ClearShoppingListUseCase(mockRepository))
    private val addToShoppingListUseCase = spyk(AddToShoppingListUseCase(mockRepository))
    private val removeFromShoppingListUseCase = spyk(RemoveFromShoppingListUseCase(mockRepository))

    private lateinit var presenter: ShoppingListPresenter

    @Before
    fun setUp() {
        presenter = ShoppingListPresenter(
                shoppingListUseCase,
                clearShoppingListUseCase,
                addToShoppingListUseCase,
                removeFromShoppingListUseCase
        )

        every { shoppingListUseCase.asSingle() } returns Single.just(MockDataTest.INGREDIENTS)
        every { clearShoppingListUseCase.asSingle() } returns Single.just(true)
        every { addToShoppingListUseCase.asSingle(any()) } returns Single.just(true)
        every { removeFromShoppingListUseCase.asSingle(any()) } returns Single.just(true)

        every { view.setupRecyclerView(any()) } just Runs
        every { view.showDialog() } just Runs
        every { view.showToast(any()) } just Runs
        every { view.fillList(any()) } just Runs
        every { view.clearList() } just Runs
        every { view.enableClearListBtn(any()) } just Runs
        every { view.showEmptyView(any()) } just Runs
        every { view.showAddIngredientDialog() } just Runs
    }

    /**
     * Scenario Showing empty view
     * Given new created presenter
     * When I attach presenter
     * Then it will show empty view and disabled clear list button.
     */
    @Test
    fun `Attach presenter if empty`() {
        //Given
        every { shoppingListUseCase.asSingle() } returns Single.just(emptyList())

        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.setupRecyclerView(emptyList())
            view.showEmptyView(true)
            view.enableClearListBtn(false)
        }
    }

    /**
     * Scenario Filling adapter with data
     * Given new created presenter
     * When I attach presenter
     * Then it will show list of ingredients and enabled clear list button.
     */
    @Test
    fun `Attach presenter if NOT empty`() {
        //When
        presenter.attach(view)

        //Then
        verifySequence {
            view.setupRecyclerView(MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL)
            view.showEmptyView(false)
            view.enableClearListBtn(true)
        }
    }

    /**
     * Scenario User clicks on clear list button and confirms
     * Given Filled shopping list
     * When User click on clear list and confirm
     * Then Shopping list ls cleared and clear list button is disabled
     */
    @Test
    fun `Clear filled shopping list`() {
        //When
        presenter.attach(view)
        presenter.onClearListBtnClicked()
        presenter.clearConfirmed()

        //Then
        verifySequence {
            //attach
            view.setupRecyclerView(MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL)
            view.showEmptyView(false)
            view.enableClearListBtn(true)

            //onClearBtnClicked
            view.showDialog()

            //clearConfirmed
            view.showToast(true)
            view.clearList()
            view.enableClearListBtn(false)
            view.showEmptyView(true)
            view.fillList(emptyList())
        }
    }

    /**
     * Scenario User clicks on item
     * Given List with items
     * When User clicks on added item
     * Then Remove item
     * When User clicks on removed item
     * Then Add item again
     */
    @Test
    fun `On added item clicked`() {
        //Given
        val updatedList = MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL.mapIndexed { index, item ->
            return@mapIndexed if (index == 0)
                item.copy(isGreyedOut = true)
            else
                item
        }
        val resultList = mutableListOf<ShoppingListItemViewModel>()
        resultList.add(updatedList[1])
        resultList.add(updatedList[2])
        resultList.add(updatedList[0])

        //When
        presenter.attach(view)
        verifySequence {
            view.setupRecyclerView(MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL)
            view.showEmptyView(false)
            view.enableClearListBtn(true)
        }
        presenter.onItemClicked(MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL.first())

        //Then
        verify {
            view.fillList(resultList)
        }

        //When
        presenter.onItemClicked(resultList.last())

        //Then
        verify {
            view.fillList(resultList.mapIndexed { index, item ->
                return@mapIndexed if (index == MockDataTest.SHOPPING_LIST_ITEM_VIEW_MODEL.size - 1)
                    item.copy(isGreyedOut = false)
                else
                    item
            })
        }
    }

    //Todo test for errors
    //    @Test
    fun fieldChangedTest() {
        assert(false)
    }
}