package com.teammealky.mealky.presentation.meals

import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.usecase.meals.ListMealsUseCase
import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MealListPresenter @Inject constructor(
        private val getMealsUseCase: ListMealsUseCase
) : BasePresenter<MealListPresenter.UI>() {

    private var totalPages: Int = 0
    private var totalElements: Int = 0
    private var pageNumber: Int = 0
    private var meals = emptyList<Meal>()
    private var visibleItemId = 0
    private var searchDisposable = CompositeDisposable()
    var isLoading = false
    var currentQuery = ""

    fun onItemClicked(model: Meal) {
        ui().perform { it.openItem(model) }
    }

    override fun attach(ui: UI) {
        super.attach(ui)
        firstRequest()
    }

    override fun destroy() {
        super.destroy()
        searchDisposable.clear()
    }

    private fun firstRequest() {
        ui().perform { it.isLoading(true) }
        if (meals.isEmpty()) {
            searchDisposable.add(getMealsUseCase.execute(
                    ListMealsUseCase.Params(page = 0, limit = LIMIT),
                    { page ->
                        totalPages = page.totalPages
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
            it.fillList(meals)
            it.setVisibleItem(visibleItemId)
            it.isLoading(false)
        }
    }

    fun loadMore() {
        searchDisposable.clear()
        ui().perform { it.isLoading(true) }
        searchDisposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(currentQuery, pageNumber, LIMIT),
                { page ->
                    ui().perform {
                        isLoading = false
                        meals += page.items
                        it.fillList(page.items)
                        it.isLoading(false)
                    }
                    if (shouldResetPages())
                        pageNumber = 0
                    else
                        pageNumber++
                },
                { e ->
                    Timber.e("KUBA_LOG Method:loadMore ***** $e *****")
                }))
    }

    private fun shouldResetPages() = allPagesFetched() && currentQuery == ""

    private fun allPagesFetched() = pageNumber >= totalPages - 1

    fun search() {
        invalidate()

        ui().perform { it.isLoading(true) }
        searchDisposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(currentQuery, 0, LIMIT),
                { page ->
                    ui().perform {
                        it.clearList()
                        it.scrollToTop()
                    }
                    totalPages = page.totalPages
                    totalElements = page.totalElements

                    loadMore()
                },
                { e ->
                    ui().perform { it.showErrorMessage({ search() }, e, false) }
                })
        )
    }

    private fun invalidate() {
        totalPages = 0
        pageNumber = 0
        meals = emptyList()
        visibleItemId = 0
        searchDisposable.clear()
    }


    fun onPaused(itemPosition: Int) {
        this.visibleItemId = itemPosition
    }

    fun scrolled(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
            ui().perform { it.hideKeyboard() }
    }

    fun isLastPage(): Boolean {
        return allPagesFetched() && currentQuery != ""
    }

    interface UI : BaseUI {
        fun setupRecyclerView()
        fun openItem(meal: Meal)
        fun isLoading(isLoading: Boolean)
        fun fillList(meals: List<Meal>)
        fun setVisibleItem(visibleItemId: Int)
        fun clearList()
        fun scrollToTop()
    }

    companion object {
        const val LIMIT = 8
    }
}