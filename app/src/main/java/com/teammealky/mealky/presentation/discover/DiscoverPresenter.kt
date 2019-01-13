package com.teammealky.mealky.presentation.discover

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.commons.extension.genRandomIntExcept
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(private val getMealsUseCase: ListMealsUseCase
) : BasePresenter<DiscoverPresenter.UI>() {
    private var currentMealId = 0
    private var meals = mutableListOf<Meal>()
    private var maxPages: Int = 0
    private var pageNumber: Int = 0
    private var totalElements: Int = 0
    private var excluded: MutableList<Int> = mutableListOf()

    fun swipedLeft() {
        currentMealId++

        if (currentMealId == totalElements - 1)
            invalidate()

        if (shouldLoadMore() && excluded.size != maxPages)
            loadMore()
    }

    fun swipedRight() {
        ui().perform { it.openItem(meals[currentMealId]) }
    }

    private fun invalidate() {
        pageNumber = 0
        currentMealId = 0
        meals = mutableListOf()
        excluded = mutableListOf()
        loadMore()
    }

    private fun shouldLoadMore() = currentMealId == (meals.size - (LIMIT - LOAD_MORE_AFTER) - 1)
            || meals.size == 0

    fun firstRequest() {
        ui().perform { it.isLoading(true) }
        if (meals.isEmpty()) {
            disposable.add(getMealsUseCase.execute(
                    ListMealsUseCase.Params(WITHOUT_CATEGORY, 0, LIMIT),
                    { page ->
                        maxPages = page.totalPages
                        totalElements = page.totalElements
                        loadMore()
                    },
                    { e ->
                        ui().perform { it.showErrorMessage({ firstRequest() }, e, false) }
                    })
            )
        } else
            refresh()
    }

    private fun refresh() {
        ui().perform {
            it.setMeals(meals.subList(currentMealId, meals.size - 1))
            it.isLoading(false)
        }
    }

    private fun loadMore() {
        pageNumber = genRandomIntExcept(0, maxPages, excluded)
        excluded.add(pageNumber)

        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(WITHOUT_CATEGORY, pageNumber, LIMIT),
                { page ->
                    meals.addAll(page.items)
                    ui().perform {
                        it.setMeals(page.items)
                        it.isLoading(false)
                    }
                    pageNumber++
                },
                { e ->
                    Timber.e("KUBA_LOG Method:loadMore ***** $e *****")
                }))
    }

    interface UI : BaseUI {
        fun openItem(meal: Meal)
        fun setMeals(meals: List<Meal>)
        fun swipeMeal()
        fun isLoading(isLoading: Boolean)
    }

    interface SwipeListener {
        fun swipedLeft()
        fun swipedRight()
    }

    companion object {
        const val LIMIT = 8
        private const val LOAD_MORE_AFTER = 6
        private const val WITHOUT_CATEGORY = -1
    }
}