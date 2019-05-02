package com.teammealky.mealky.data.repository

import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.domain.repository.AddMealRepository
import com.teammealky.mealky.domain.service.RestService
import io.reactivex.Completable
import okhttp3.MediaType
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@Singleton
class AddMealDataRepository @Inject constructor(private val api: RestService) : AddMealRepository {

    override fun addMeal(meal: Meal, images: List<File>): Completable {
        val parts = images.map { file ->
            val request = RequestBody.create(
                    MediaType.parse("image/*"),
                    file
            )
            return@map MultipartBody.Part.createFormData("file", file.name, request)
        }

        return api.client().addImages(parts).flatMapCompletable { imagesFromApi ->
            api.client().addMeal(meal.copy(images = imagesFromApi.urls))
        }
    }
}