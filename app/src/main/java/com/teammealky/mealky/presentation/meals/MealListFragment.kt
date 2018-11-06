package com.teammealky.mealky.presentation.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.meals_fragment.*

class MealListFragment : BaseFragment<MealListPresenter, MealListPresenter.UI, MealListViewModel>(), MealListPresenter.UI, MealsAdapter.OnItemClickListener {

    override val vmClass = MealListViewModel::class.java

    private lateinit var viewAdapter: MealsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.meals_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRefreshLayout()
    }

    private fun setupRefreshLayout() {
        swipeContainer.setOnRefreshListener {
            presenter?.refresh()
        }
    }

    override fun fillList(meals: List<Meal>) {
        viewManager = LinearLayoutManager(context)
        setRecyclerViewAdapter(meals)

        mealListRv.adapter = viewAdapter
        mealListRv.setHasFixedSize(true)
        mealListRv.layoutManager = viewManager

        mealListRv.addItemDecoration(DividerItemDecoration(mealListRv.context, LinearLayoutManager.VERTICAL))
    }

    override fun refreshList(meals: List<Meal>) {
        viewAdapter.refreshItems(meals)
    }

    private fun setRecyclerViewAdapter(meals: List<Meal>) {
        viewAdapter = MealsAdapter(meals, this)
    }

    override fun isLoading(isLoading: Boolean) {
        swipeContainer.isRefreshing = isLoading
    }

    override fun openItem(meal: Meal) {
        context?.let {
            Navigator.from(it as Navigator.Navigable).openMeal(meal)
        }
    }

    private fun setupRecyclerView() {
        presenter?.loadMeals()
    }

    override fun removeFromList(meal: Meal) {
    }

    override fun onItemClick(item: Meal) {
        presenter?.onItemClicked(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

}
