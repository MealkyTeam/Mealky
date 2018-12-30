package com.teammealky.mealky.meal

import org.junit.Test
import com.teammealky.mealky.MockDataAndroid
import org.junit.Assert.*
import com.teammealky.mealky.presentation.meal.MealMapper

class MealMapperTest{

    @Test
    fun readFromBundle(){
        val meal1 = MockDataAndroid.MEALS[0]
        val bundle1 = MealMapper.writeExtra(meal1)
        val mealFromBundle1 = MealMapper.readExtra(bundle1)

        val meal2 = MockDataAndroid.MEALS[1]
        val bundle2 = MealMapper.writeExtra(meal2)
        val mealFromBundle2 = MealMapper.readExtra(bundle2)

        val meal3 = MockDataAndroid.MEALS[2]
        val bundle3 = MealMapper.writeExtra(meal3)
        val mealFromBundle3 = MealMapper.readExtra(bundle3)

        assertEquals(meal1,mealFromBundle1)
        assertEquals(meal2,mealFromBundle2)
        assertEquals(meal3,mealFromBundle3)
    }

}