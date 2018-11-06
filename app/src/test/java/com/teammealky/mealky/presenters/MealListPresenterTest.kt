package com.teammealky.mealky.presenters

import com.teammealky.mealky.domain.repository.MealsRepository
import com.teammealky.mealky.presentation.meals.MealListPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MealListPresenterTest {


    @Mock
    lateinit var mockRepository: MealsRepository

    private lateinit var presenter: MealListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadMealsIfRepoIsEmpty() {
        // Mockito.`when`((mockRepository.getAll())).thenReturn(emptyList<Meal>())
        presenter.loadMeals()
        assert(false)
    }

    @Test
    fun loadMealsIfRepoIsNotEmpty() {

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