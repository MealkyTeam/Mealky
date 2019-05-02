package com.teammealky.mealky.presentation.meal.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import kotlinx.android.synthetic.main.ingredient_item.view.*

@SuppressLint("ViewConstructor")
open class IngredientView @JvmOverloads constructor(
        context: Context,
        private val listener: IngredientsAdapter.OnItemClickListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), View.OnClickListener {

    var model: IngredientViewModel? = null
        set(value) {
            field = value
            bind()
        }

    init {
        this.inflateLayout()
    }

    open fun inflateLayout() {
        inflate(R.layout.ingredient_item, true)
    }

    @SuppressLint("SetTextI18n")
    open fun bind() {
        model?.let { vm ->
            val ingredient = vm.item

            ingredientTv.text = ingredient.name.capitalize() + ": " + ingredient.quantity + " " + ingredient.unit.name
            checkbox.isChecked = vm.isChecked

            ingredientLayout.setOnClickListener {
                checkbox.performClick()
            }

            checkbox.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.checkbox -> listener.onItemClick(model!!)
            else -> checkbox.performClick()
        }
    }
}