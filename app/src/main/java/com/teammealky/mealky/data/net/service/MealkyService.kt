package com.teammealky.mealky.data.net.service

import com.teammealky.mealky.domain.model.Ingredient
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.PasswordSignInRequest
import com.teammealky.mealky.domain.model.SignUpRequest
import com.teammealky.mealky.domain.model.TokenSignInRequest
import com.teammealky.mealky.domain.model.User
import com.teammealky.mealky.domain.model.Unit
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MealkyService {

    @GET("meals/{id}")
    fun getMeal(@Path("id") id: Int): Single<Meal>

    @GET("meals")
    fun listMeals(
            @Query("category") categoryId: Int,
            @Query("page") offset: Int,
            @Query("size") limit: Int,
            @Query("sort") sort: String? = "id,desc"
    ): Single<Page<Meal>>

    @GET("meals")
    fun searchMeals(
            @Query("q") query: String,
            @Query("page") offset: Int,
            @Query("size") limit: Int
    ): Single<Page<Meal>>

    @GET("/sec/ingredients")
    fun searchIngredients(
            @Query("q") query: String,
            @Query("limit") limit: Int
    ): Single<Page<Ingredient>>

    @GET("/sec/units")
    fun searchUnits(
            @Query("q") query: String,
            @Query("limit") limit: Int
    ): Single<Page<Unit>>

    @POST("/sec/login")
    fun signInWithPassword(@Body request: PasswordSignInRequest): Single<User>

    @POST("/sec/login")
    fun signInWithToken(@Body request: TokenSignInRequest): Single<User>

    @POST("/sec/signup")
    fun signUp(@Body request: SignUpRequest): Completable
}