package com.kuba.mealky

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kuba.mealky.Database.DBWorkerThread
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.MealkyDatabase

class MainActivity : AppCompatActivity() {

    private var database: MealkyDatabase? = null
    private lateinit var mDbWorkerThread: DBWorkerThread
    private val mUiHandler = Handler()


    private lateinit var mName: TextView
    private lateinit var mPrepTime: TextView
    private lateinit var mIngredients: TextView
    private lateinit var mPreparation: TextView
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDbWorkerThread = DBWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        database = MealkyDatabase.getInstance(this)

        mName = findViewById(R.id.name)
        mPrepTime = findViewById(R.id.prepTime)
        mIngredients = findViewById(R.id.ingredients)
        mPreparation = findViewById(R.id.preparation)
        fetchMealsFromDb()
        mButton = findViewById(R.id.button)
        mButton.setOnClickListener({
            val meal = MealData()
            meal.name = "test name"
            meal.prep_time = 0
            meal.preparation = "test prep"
            val task = Runnable { database?.mealDao()?.insert(meal) }
            mDbWorkerThread.postTask(task)
        })
    }

    private fun fetchMealsFromDb() {
        val task = Runnable {
            val meals =
                    database?.mealDao()?.getAll()
            mUiHandler.post({
                if (meals == null || meals?.size == 0) {
                    Toast.makeText(this, "No data in cache..!!", Toast.LENGTH_SHORT).show()
                } else {
                    bindDataWithUi(meal = meals?.get(0))
                }
            })
        }
        mDbWorkerThread.postTask(task)
    }

    private fun bindDataWithUi(meal: MealData?) {
        mName.text = meal?.name
        mPrepTime.text = meal?.prep_time.toString()
        mPreparation.text = meal?.preparation
        //TODO mIngredients.text = weatherData?.name
    }

    override fun onDestroy() {
        MealkyDatabase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}
