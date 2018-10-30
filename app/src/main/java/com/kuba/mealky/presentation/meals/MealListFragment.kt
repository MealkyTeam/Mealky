package com.kuba.mealky.presentation.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuba.mealky.R
import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.presentation.App
import com.kuba.mealky.presentation.commons.Navigator
import com.kuba.mealky.presentation.commons.presenter.BaseFragment
import com.kuba.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.meals_fragment.*
import timber.log.Timber

class MealListFragment : BaseFragment<MealListPresenter, MealListPresenter.UI, MealListViewModel>(), MealListPresenter.UI {

    override val vmClass = MealListViewModel::class.java

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)

        presenter = MealListPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.meals_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun fillList(meals: MutableList<Meal>) {
        viewManager = LinearLayoutManager(context)
        setRecyclerViewAdapter(meals)

        mealListRv.adapter = viewAdapter
        mealListRv.setHasFixedSize(true)
        mealListRv.layoutManager = viewManager

        mealListRv.addItemDecoration(DividerItemDecoration(mealListRv.context, LinearLayoutManager.VERTICAL))
    }

    private fun setRecyclerViewAdapter(meals: MutableList<Meal>) {
        viewAdapter = MealsAdapter(meals, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {
                presenter?.onItemClicked(item)
            }
        })
    }

    override fun openItem(meal: Meal) {
        context?.let{
            Navigator.from(it as Navigator.Navigable).openMeal(meal)
        }
    }

    private fun setupRecyclerView() {
        presenter?.loadMeals()
    }

    override fun removeFromList(meal: Meal) {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

}
