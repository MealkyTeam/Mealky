package com.teammealky.mealky.adapters

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.meals.adapter.MealsAdapter
import org.junit.Before
import org.junit.Test

class MealsAdapterTest {
    private lateinit var adapter: MealsAdapter
    lateinit var dummyList: MutableList<Meal>
    @Before
    fun setUp() {
        dummyList = mutableListOf(
                Meal(1, "test1", 0, "testPrep1", listOf("https://cdn.pixabay.com/photo/2014/06/11/17/00/cook-366875__480.jpg")),
                Meal(2, "test2", 0, "testPrep2",listOf("https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032__480.jpg")),
                Meal(3, "test3", 0, "testPrep3",listOf("https://cdn.pixabay.com/photo/2017/06/06/22/46/mediterranean-cuisine-2378758__480.jpg"))
        )
        adapter = MealsAdapter(dummyList, object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {
            }
        })
    }

    @Test
    fun onCreateViewHolder() {
        assert(true)
    }

    @Test
    fun onBindViewHolder() {
        assert(true)
    }

    @Test
    fun getItemCount() {
        assert(true)

    }

    @Test
    fun getItem() {
        assert(true)
    }

    @Test
    fun removeAtTest() {
        assert(true)
    }

}