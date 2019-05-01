package com.teammealky.mealky.presentation.addmeal.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter.OnItemClickListener
import com.teammealky.mealky.presentation.shoppinglist.view.ShoppingListItemView
import kotlinx.android.synthetic.main.add_ingredient_list_item.view.*
import com.teammealky.mealky.presentation.commons.view.IngredientQuantityView.Companion.FieldChangedListener

@SuppressLint("ViewConstructor")
class AddIngredientListItemView @JvmOverloads constructor(
        context: Context,
        onClickListener: OnItemClickListener,
        private val fieldChangedListener: FieldChangedListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : ShoppingListItemView(context, onClickListener, fieldChangedListener, attrs, defStyle) {

    override fun inflateLayout() {
        inflate(R.layout.add_ingredient_list_item, true)
        addIngredientItemLayout.setOnClickListener(this)
        removeBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.removeBtn -> onClickListener.onItemClick(model!!)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind() {
        model?.let {
            quantityView.model = it
            quantityView.listener = fieldChangedListener
        }
    }
}