package com.teammealky.mealky.presentation.discover

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.discover.DiscoverUseCase
import com.teammealky.mealky.presentation.commons.extension.genRandomIntExcept
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(private val getMealsByPage: DiscoverUseCase
) : BasePresenter<DiscoverPresenter.UI>() {
    private var currentMealId = 0
    private var meals = mutableListOf<Meal>()
    private var maxPages: Int = 0
    private var pageNumber: Int = 0
    private var totalElements: Int = 0
    private var excluded: MutableList<Int> = mutableListOf()

    fun likeClicked() {
        ui().perform { it.openItem(meals[currentMealId]) }
    }

    fun dislikeClicked(fromSwipe: Boolean) {
        currentMealId++

        if (currentMealId == totalElements) {
            invalidate()
        }
        if (shouldLoadMore()) {
            loadMore(!fromSwipe)
        } else if (!fromSwipe) {
            ui().perform { it.swipeMeal() }
        }
    }

    private fun invalidate() {
        pageNumber = 0
        currentMealId = 0
        meals = mutableListOf()
        excluded = mutableListOf()
    }

    private fun shouldLoadMore() = currentMealId == LOAD_MORE_AFTER ||
            currentMealId % LOAD_MORE_AFTER + LIMIT == 0
            || meals.size == 0

    fun firstRequest() {
        disposable.add(getMealsByPage.execute(
                DiscoverUseCase.Params(0, LIMIT),
                { page ->
                    maxPages = page.totalPages
                    totalElements = page.totalElements
                    loadMore(false)
                },
                { e ->
                    Timber.e("FunName:loadMore *****ERROR: $e *****")
                }))
    }

    private fun loadMore(withSwipe: Boolean) {
        ui().perform { it.isLoading(true) }
        pageNumber = genRandomIntExcept(0, maxPages, excluded)
        excluded.add(pageNumber)
        disposable.add(getMealsByPage.execute(
                DiscoverUseCase.Params(pageNumber, LIMIT),
                { page ->
                    meals.addAll(page.meals)

                    ui().perform {
                        it.setMeals(meals)
                        it.isLoading(false)
                        if (withSwipe) it.swipeMeal()
                    }
                    pageNumber++
                },
                { e ->
                    Timber.e("FunName:loadMore *****ERROR: $e *****")
                }))
    }

    interface UI : BaseUI {
        fun openItem(meal: Meal)
        fun setMeals(meals: List<Meal>)
        fun swipeMeal()
        fun isLoading(isLoading: Boolean)
    }

    companion object {
        private const val LIMIT = 3
        private const val LOAD_MORE_AFTER = 2
    }
}