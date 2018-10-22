package com.kuba.mealky.presentation.meals

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuba.mealky.R
import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.presentation.commons.callbacks.SwipeToDeleteCallback
import com.kuba.mealky.presentation.commons.presenter.BaseActivity
import com.kuba.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MealListActivity : BaseActivity<MealListPresenter, MealListPresenter.UI, MealListViewModel>(), MealListPresenter.UI {

    override val vmClass = MealListViewModel::class.java
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun fillList(meals: MutableList<Meal>) {
        viewManager = LinearLayoutManager(this)
        setRecyclerViewAdapter(meals)

        recyclerView = mealList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL))

        setupSwipeHandler()
    }

    private fun setupSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val mealToDelete = (viewAdapter as MealsAdapter).getItem(viewHolder.adapterPosition)
                    if (mealToDelete != null) {
                        presenter?.deleteMeal(mealToDelete, viewHolder.adapterPosition)
                    }
                } catch (e: Exception) {

                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setRecyclerViewAdapter(meals: MutableList<Meal>) {
        viewAdapter = MealsAdapter(meals, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {

            }
        })
    }

    override fun goToMeal(meal: Meal) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.topBar))
        bottomBar = findViewById(R.id.bottomBar)

        presenter = MealListPresenter()

        setupRecyclerView()
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
