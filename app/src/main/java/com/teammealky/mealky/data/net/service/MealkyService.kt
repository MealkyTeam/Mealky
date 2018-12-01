package com.teammealky.mealky.data.net.service

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.model.Page
import com.teammealky.mealky.domain.model.PasswordRequest
import com.teammealky.mealky.domain.model.TokenRequest
import io.reactivex.Single
import retrofit2.Response
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
    ): Single<Page>

    @GET("meals")
    fun searchMeals(
            @Query("q") query: String,
            @Query("page") offset: Int,
            @Query("size") limit: Int
    ): Single<List<Meal>>

    @POST("/sec/login")
    fun signInWithPassword(@Body request: PasswordRequest): Single<String>

    @POST("/sec/login")
    fun signInWithToken(@Body request: TokenRequest): Single<Response<Void>>

    @POST("/sec/signup")
    fun signUp(@Query("username") username: String,
               @Query("email") email: String,
               @Query("password") password: String)
}