package com.teammealky.mealky.presentation.meal

import android.os.Bundle
import com.teammealky.mealky.domain.model.Meal

object MealMapper {

    fun writeExtra(model: Meal): Bundle {
        val bundle = Bundle()

        bundle.putInt(ID, model.id)
        bundle.putString(NAME, model.name)
        bundle.putInt(PREP_TIME, model.prepTime)
        bundle.putString(PREPARATION, model.preparation)
        bundle.putStringArrayList(IMAGES, ArrayList(model.images))

        return bundle
    }

    fun readExtra(bundle: Bundle?): Meal? {
        if (null == bundle) return null

        val id = bundle.getInt(ID)
        val name = bundle.getString(NAME, "")
        val prepTime = bundle.getInt(PREP_TIME)
        val preparation = bundle.getString(PREPARATION, "")
        val images = bundle.getStringArrayList(IMAGES) ?: ArrayList()

        return Meal(id, name, prepTime, preparation, images)
    }

    private const val ID = "id"
    private const val NAME = "name"
    private const val PREP_TIME = "prep_time"
    private const val PREPARATION = "preparation"
    private const val IMAGES = "images"
}
