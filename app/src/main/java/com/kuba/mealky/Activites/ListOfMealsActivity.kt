package com.kuba.mealky.Activites

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.kuba.mealky.Adapters.MealsAdapter
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase
import com.kuba.mealky.Presenters.ListOfMealsContract
import com.kuba.mealky.Presenters.ListOfMealsPresenter
import com.kuba.mealky.R

class ListOfMealsActivity : AppCompatActivity(), ListOfMealsContract.View {
    private lateinit var meals: List<MealData>
    lateinit var bottomBar: BottomNavigationView
    lateinit var presenter: ListOfMealsPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun fillList(m: List<MealData>) {
        meals = m
        Log.e("MealsSizeInFillList", meals.size.toString())
        viewManager = LinearLayoutManager(this)
        viewAdapter = MealsAdapter(meals)

        recyclerView = findViewById<RecyclerView>(R.id.list_of_meals).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onItemClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemLongClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearData() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.topBar))
        bottomBar = findViewById(R.id.bottomBar)
        presenter = ListOfMealsPresenter(MealkyDatabase.getInstance(this)!!)
        presenter.attach(this)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        presenter.loadMeals()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearData()
    }

}


