package com.kuba.mealky.Activites

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.kuba.mealky.R
import com.kuba.mealky.Views.ListOfMealsView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.MyAdapter

class MainActivity : AppCompatActivity(), ListOfMealsView {
    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemLongClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var bottomBar: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.topBar))
        bottomBar = findViewById(R.id.bottomBar)


        val meals: Array<MealData> = arrayOf(
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(1, "meal1", 31, "prep1"),
                MealData(2, "meal2", 32, "prep2"),
                MealData(3, "meal3", 33, "prep3"),
                MealData(4, "meal4", 34, "prep4")
        )
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(meals)

        recyclerView = findViewById<RecyclerView>(R.id.list_of_meals).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }


    }

}
