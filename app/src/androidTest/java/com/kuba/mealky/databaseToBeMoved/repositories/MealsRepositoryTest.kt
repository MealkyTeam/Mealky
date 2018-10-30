package com.kuba.mealky.databaseToBeMoved.repositories

import androidx.room.Room
import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.domain.repository.MealsRepository
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class MealsRepositoryTest {
    private lateinit var repository: MealsRepository
}