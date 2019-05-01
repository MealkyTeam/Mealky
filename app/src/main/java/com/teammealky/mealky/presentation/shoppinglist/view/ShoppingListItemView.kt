package com.teammealky.mealky.presentation.shoppinglist.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.view.IngredientQuantityView.Companion.FieldChangedListener
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.view.IngredientView
import kotlinx.android.synthetic.main.ingredient_quantity_view.view.*
import kotlinx.android.synthetic.main.shopping_list_item.view.*

@SuppressLint("ViewConstructor")
open class ShoppingListItemView @JvmOverloads constructor(
        context: Context,
        val onClickListener: IngredientsAdapter.OnItemClickListener,
        private val fieldChangedListener: FieldChangedListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : IngredientView(context, onClickListener, attrs, defStyle) {


    override fun inflateLayout() {
        inflate(R.layout.shopping_list_item, true)
        shoppingListItemLayout.foreground = null
    }

    @SuppressLint("SetTextI18n")
    override fun bind() {
        model?.let { vm ->
            quantityView.model = vm
            quantityView.listener = fieldChangedListener

            bullet.setImageResource(if (vm.isChecked) R.drawable.ic_bullet_grey_16dp else R.drawable.ic_bullet_black_16dp)
            checkbox.buttonDrawable =
                    ContextCompat.getDrawable(context,
                            if (vm.isChecked) R.drawable.checkbox_selector_grey else R.drawable.checkbox_selector_grey)
            checkbox.isChecked = vm.isChecked

            strikethrough.isVisible(vm.isChecked)

            ingredientNameTv.setTextColor(ContextCompat.getColor(context, if (vm.isChecked) R.color.colorPrimary else R.color.text_primary))
            quantityTv.setTextColor(ContextCompat.getColor(context, if (vm.isChecked) R.color.colorPrimary else R.color.text_primary))
            unitTv.setTextColor(ContextCompat.getColor(context, if (vm.isChecked) R.color.colorPrimary else R.color.text_primary))

            shoppingListItemLayout.setOnClickListener(this)
            checkbox.setOnClickListener(this)
        }
    }
}