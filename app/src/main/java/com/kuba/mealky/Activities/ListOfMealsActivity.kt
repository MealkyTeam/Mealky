package com.kuba.mealky.Activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.kuba.mealky.Adapters.MealsAdapter
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase
import com.kuba.mealky.Database.Repositories.MealsRepository
import com.kuba.mealky.Presenters.ListOfMealsContract
import com.kuba.mealky.Presenters.ListOfMealsPresenter
import com.kuba.mealky.R

class ListOfMealsActivity : AppCompatActivity(), ListOfMealsContract.View {


    private lateinit var meals: List<MealData>
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var presenter: ListOfMealsPresenter
    private lateinit var repository: MealsRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TAG: String = "KubaTag"
    }

    override fun fillList(m: List<MealData>) {
        meals = m

        viewManager = LinearLayoutManager(this)
        viewAdapter = MealsAdapter(meals, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: MealData) {
                Log.e(TAG, "TEST ON CLICK")
            }
        })

        recyclerView = findViewById<RecyclerView>(R.id.list_of_meals).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onItemClick() {
    }

    override fun onItemLongClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.topBar))
        bottomBar = findViewById(R.id.bottomBar)

        repository = MealsRepository(MealkyDatabase.getInstance(this)!!)
        presenter = ListOfMealsPresenter(repository)
        presenter.attach(this)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        presenter.loadMeals()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

}


