package com.teammealky.mealky.presentation.meals

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import timber.log.Timber
import javax.inject.Inject


class MealListPresenter @Inject constructor(
        private val getMealsUseCase: ListMealsUseCase
) : BasePresenter<MealListPresenter.UI>() {

    private var maxPages: Int = 0
    private var pageNumber: Int = 0
    private var meals = emptyList<Meal>()
    private var visibleItemId = 0

    fun onItemClicked(model: Meal) {
        ui().perform { it.openItem(model) }
    }

    fun firstRequest() {
        ui().perform { it.isLoading(true) }
        if (meals.isEmpty()) {
            disposable.add(getMealsUseCase.execute(
                    ListMealsUseCase.Params(WITHOUT_CATEGORY, 0, LIMIT),
                    { page ->
                        maxPages = page.totalPages
                        loadMore()
                    },
                    { e ->
                        Timber.d("KUBA Method:firstRequest ***** ERROR: $e *****")
                    })
            )
        } else
            refresh()
    }

    private fun refresh() {
        ui().perform {
            it.fillList(meals)
            it.setVisibleItem(visibleItemId)
            it.isLoading(false)
        }
    }

    fun loadMore() {
        disposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(WITHOUT_CATEGORY, pageNumber, LIMIT),
                { page ->
                    ui().perform {
                        meals += page.meals
                        it.fillList(page.meals)
                        it.isLoading(false)
                    }
                    if (pageNumber >= maxPages - 1)
                        pageNumber = 0
                    else
                        pageNumber++
                },
                { e ->
                    Timber.d("KUBA Method:loadMore ***** ERROR: $e *****")
                }))
    }

    fun onPaused(itemPosition: Int) {
        this.visibleItemId = itemPosition
    }

    interface UI : BaseUI {
        fun setupRecyclerView()
        fun openItem(meal: Meal)
        fun isLoading(isLoading: Boolean)
        fun fillList(meals: List<Meal>)
        fun setVisibleItem(visibleItemId: Int)
    }

    companion object {
        const val LIMIT = 8
        private const val WITHOUT_CATEGORY = -1
    }
}