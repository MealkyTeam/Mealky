package com.teammealky.mealky.presentation.commons.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//thanks to Kitek
open class InfiniteScrollListener(
        private val func: () -> Unit,
        private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var firstVisibleItem: Int = 0
    private var previousTotalCount: Int = 0
    private val visibleThreshold: Int = 6

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // Adapter has been reloaded
        if (isLoading && totalItemCount > layoutManager.itemCount) isLoading = false

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (previousTotalCount == 0) previousTotalCount = totalItemCount
        if (isLoading) {
            if (totalItemCount > previousTotalCount) {
                isLoading = false
                previousTotalCount = totalItemCount
            }
        }
        if (!isLoading && (totalItemCount - visibleItemCount) < (firstVisibleItem + visibleThreshold)) {
            isLoading = true
            func()
        }
    }
}
