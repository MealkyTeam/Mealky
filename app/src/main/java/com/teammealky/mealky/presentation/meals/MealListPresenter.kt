package com.teammealky.mealky.presentation.meals

import android.os.Parcelable
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
    private var savedRecyclerViewPosition: Parcelable? = null
    private var searchDisposable = CompositeDisposable()
    private var forceReload = false
    var isLoading = false
    var isLast = false
    var currentQuery = ""


    fun onItemClicked(model: Meal) {
        ui().perform { it.openItem(model) }
    }

    override fun destroy() {
        super.destroy()
        searchDisposable.clear()
    }

    fun firstRequest() {
        if (meals.isEmpty()) {
            ui().perform { it.isLoading(true) }
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
            it.clearList()
            it.fillList(meals)
            it.scrollToSaved(savedRecyclerViewPosition)
        }
    }

    fun loadMore() {
        searchDisposable.clear()
        ui().perform { it.isLoading(true) }
        searchDisposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(currentQuery, pageNumber, LIMIT),
                { page ->
                    isLast = page.last
                    ui().perform {
                        isLoading = false
                        meals = meals + page.items
                        it.fillList(page.items)
                        it.isLoading(false)
                        it.showEmptyView(meals.isEmpty(), currentQuery)
                    }
                    if (shouldResetPages())
                        pageNumber = 0
                    else
                        pageNumber++
                },
                { e ->
                    ui().perform { it.isLoading(false) }
                    Timber.e("KUBA_LOG Method:loadMore ***** $e *****")
                }))
    }

    private fun shouldResetPages() = isLast && currentQuery == ""

    fun search() {
        invalidate()

        ui().perform { it.isLoading(true) }
        searchDisposable.add(getMealsUseCase.execute(
                ListMealsUseCase.Params(currentQuery, 0, LIMIT, forceReload),
                { page ->
                    ui().perform {
                        it.clearList()
                        it.scrollToTop()
                    }
                    totalPages = page.totalPages
                    totalElements = page.totalElements
                    forceReload = false
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
        isLast = false
        savedRecyclerViewPosition = null
        searchDisposable.clear()
    }

    fun onPaused(savedRecyclerView: Parcelable?) {
        this.savedRecyclerViewPosition = savedRecyclerView
    }

    fun invalidateList(invalidateList: Boolean) {
        if (invalidateList) {
            forceReload = true
            currentQuery = ""
            ui().perform { it.clearSearchText() }
            search()
        }
    }

    fun swipedContainer() {
        forceReload = true
        ui().perform { it.stopSpinner() }
        search()
    }
    fun shouldStopLoadMore(): Boolean {
        return isLast && currentQuery != ""
    }

    fun onAddMealBtnClicked() {
        ui().perform { it.openAddMeal() }
    }

    fun fragmentReselected() {
        ui().perform { it.scrollToTop(true) }
    }

    interface UI : BaseUI {
        fun setupRecyclerView()
        fun openItem(meal: Meal)
        fun isLoading(isLoading: Boolean)
        fun fillList(meals: List<Meal>)
        fun scrollToSaved(savedRecyclerView: Parcelable?)
        fun clearList()
        fun scrollToTop(animate: Boolean = false)
        fun showEmptyView(isVisible: Boolean, query: String = "")
        fun openAddMeal()
        fun stopSpinner()
        fun clearSearchText()
    }

    companion object {
        const val LIMIT = 8
    }
}