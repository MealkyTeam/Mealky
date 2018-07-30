package com.kuba.mealky.presenters

import com.kuba.mealky.database.models.Meal
import com.kuba.mealky.database.repositories.MealsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
        // Mockito.`when`((mockRepository.getAll())).thenReturn(emptyList<Meal>())
        presenter.loadMeals()
        assert(false)
    }

    @Test
    fun loadMealsIfRepoIsNotEmpty() {
        Mockito.`when`((mockRepository.getAll())).thenReturn(
                mutableListOf(
                        Meal(1, "test1", 0, "testPrep1"),
                        Meal(2, "test2", 0, "testPrep2"),
                        Meal(3, "test3", 0, "testPrep3")
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