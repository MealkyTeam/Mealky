package com.teammealky.mealky.presentation.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.listener.InfiniteScrollListener
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.meals_fragment.*

class MealListFragment : BaseFragment<MealListPresenter, MealListPresenter.UI, MealListViewModel>(), MealListPresenter.UI, MealsAdapter.OnItemClickListener {

    override val vmClass = MealListViewModel::class.java

    private lateinit var adapter: MealsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.meals_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        adapter = MealsAdapter(listener = this)

        mealListRv.adapter = adapter
        mealListRv.setHasFixedSize(true)
        mealListRv.layoutManager = layoutManager
        mealListRv.addOnScrollListener(InfiniteScrollListener({ presenter?.loadMore() }, layoutManager as LinearLayoutManager))
    }

    override fun fillList(meals: List<Meal>) {
        adapter.addItems(meals)
    }

    override fun setVisibleItem(visibleItemId: Int) {
        layoutManager.scrollToPosition(visibleItemId)
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
    }

    override fun openItem(meal: Meal) {
            Navigator.from(context as Navigator.Navigable).openMeal(meal)
    }

    override fun onItemClick(item: Meal) {
        presenter?.onItemClicked(item)
    }

    override fun onPause() {
        val itemPosition = (mealListRv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        presenter?.onPaused(itemPosition)

        super.onPause()
    }

}
