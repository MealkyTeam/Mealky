package com.teammealky.mealky.data.net.service

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealkyService {

    @GET("meals/{id}")
    fun getMeal(@Path("id") id: Int): Single<Meal>

    @GET("meals")
    fun listMeals(
            @Query("category") categoryId: Int,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
    ): Single<List<Meal>>

    @GET("discovery")
    fun discover(
            @Query("page") page: Int,
            @Query("size") limit: Int
    ): Single<Page>

    @GET("meals")
    fun searchMeals(
            @Query("q") query: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
    ): Single<List<Meal>>

}