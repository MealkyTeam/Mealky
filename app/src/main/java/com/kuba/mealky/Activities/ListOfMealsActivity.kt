package com.kuba.mealky.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.SimpleAdapter
import com.kuba.mealky.Adapters.MealsAdapter
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase
import com.kuba.mealky.Database.Repositories.MealsRepository
import com.kuba.mealky.Presenters.ListOfMealsContract
import com.kuba.mealky.Presenters.ListOfMealsPresenter
import com.kuba.mealky.R
import com.kuba.mealky.util.SwipeToDeleteCallback

class ListOfMealsActivity : AppCompatActivity(), ListOfMealsContract.View {


    private lateinit var meals: MutableList<MealData>
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var presenter: ListOfMealsPresenter
    private lateinit var repository: MealsRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TAG: String = "KubaTag"
    }

    override fun fillList(m: MutableList<MealData>) {
        meals = m

        viewManager = LinearLayoutManager(this)
        setRecyclerViewAdapter(meals)

        recyclerView = findViewById<RecyclerView>(R.id.list_of_meals).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        setupSwipeHandler()
    }

    private fun setupSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val mealToDelete = (viewAdapter as MealsAdapter).getItem(viewHolder.adapterPosition)
                    if (mealToDelete != null) {
                        presenter.deleteMeal(mealToDelete, viewHolder.adapterPosition)
                    } else
                        Log.e(TAG, "Meal is null after swipe")
                } catch (e: Exception) {
                    Log.e(TAG, e.printStackTrace().toString())
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setRecyclerViewAdapter(m: MutableList<MealData>) {
        viewAdapter = MealsAdapter(m, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: MealData) {
                Log.e(TAG, "TEST ON CLICK")
                val intent = Intent()
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
                    MealData(1, "test1", 0, "testPrep1"),
                    MealData(2, "test2", 0, "testPrep2"),
                    MealData(3, "test3", 0, "testPrep3")
            )
            repository.insertList(dummyList)
        }
        val thread = Thread(task)
        thread.start()
        thread.join()
        //

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


