package com.kuba.mealky.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuba.mealky.R
import com.kuba.mealky.adapters.MealsAdapter
import com.kuba.mealky.database.MealkyDatabase
import com.kuba.mealky.database.models.Meal
import com.kuba.mealky.database.repositories.MealsRepository
import com.kuba.mealky.presenters.ListOfMealsContract
import com.kuba.mealky.presenters.ListOfMealsPresenter
import com.kuba.mealky.util.SwipeToDeleteCallback

class ListOfMealsActivity : AppCompatActivity(), ListOfMealsContract.View {
    private lateinit var meals: MutableList<Meal>
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var presenter: ListOfMealsPresenter
    private lateinit var repository: MealsRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun fillList(m: MutableList<Meal>) {
        meals = m

        viewManager = LinearLayoutManager(this)
        setRecyclerViewAdapter(meals)

        recyclerView = findViewById<RecyclerView>(R.id.list_of_meals).apply {
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
                        presenter.deleteMeal(mealToDelete, viewHolder.adapterPosition)
                    }
                } catch (e: Exception) {

                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setRecyclerViewAdapter(m: MutableList<Meal>) {
        viewAdapter = MealsAdapter(m, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {
            }
        })
    }

    override fun onItemClick() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.topBar))
        bottomBar = findViewById(R.id.bottomBar)


        repository = MealsRepository(MealkyDatabase.getInstance(this)!!)

        //Fill with dummyData
        val task = Runnable {
            val dummyList = mutableListOf(
                    Meal(1, "test1", 120, "testPrep1"),
                    Meal(2, "test2", 54, "testPrep2"),
                    Meal(3, "test3", 13, "testPrep3")
            )
            repository.insertList(dummyList)
        }
        val thread = Thread(task)
        thread.start()
        thread.join()

        presenter = ListOfMealsPresenter(repository)
        presenter.attach(this)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        presenter.loadMeals()
    }

    override fun removeFromList(index: Int) {
        (viewAdapter as MealsAdapter).removeAt(index)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

}
