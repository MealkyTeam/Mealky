package com.kuba.mealky.Presenters

import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.Database.Repositories.MealsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito

class ListOfMealsPresenterTest {


    @Mock
    lateinit var mockRepository: MealsRepository

    lateinit var presenter: ListOfMealsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ListOfMealsPresenter(mockRepository)
    }

    @Test
    fun loadMealsIfRepoIsEmpty() {
        Mockito.`when`((mockRepository.getAll())).thenReturn(emptyList<MealData>())
        presenter.loadMeals()
        assert(false)
    }

    @Test
    fun loadMealsIfRepoIsNotEmpty() {
        Mockito.`when`((mockRepository.getAll())).thenReturn(
                mutableListOf(
                        MealData(1, "test1", 0, "testPrep1"),
                        MealData(2, "test2", 0, "testPrep2"),
                        MealData(3, "test3", 0, "testPrep3")
                ))

        assert(false)
    }

    @Test
    fun changeViewToMeal() {
        assert(false)
    }

    @Test
    fun deleteMeal() {
        assert(false)
    }
}