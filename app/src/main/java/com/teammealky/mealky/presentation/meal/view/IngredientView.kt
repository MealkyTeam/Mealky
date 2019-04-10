package com.teammealky.mealky.presentation.meal.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import kotlinx.android.synthetic.main.ingredient_item.view.*
import timber.log.Timber

@SuppressLint("ViewConstructor")
class IngredientView @JvmOverloads constructor(
        context: Context,
        private val listener: IngredientsAdapter.OnItemClickListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    var model: IngredientViewModel? = null
        set(value) {
            field = value
            bind()
        }

    init {
        inflate(R.layout.ingredient_item, true)
    }

    @SuppressLint("SetTextI18n")
    private fun bind() {
        model?.let { vm ->
            val ingredient = vm.item

            ingredientTv.text = ingredient.name.capitalize() + ": " + ingredient.quantity + " " + ingredient.unit.name
            checkbox.isChecked = vm.isChecked

            ingredientLayout.setOnClickListener {
                checkbox.performClick()
            }

            checkbox.setOnClickListener { listener.onItemClick(vm) }
        }
    }
}