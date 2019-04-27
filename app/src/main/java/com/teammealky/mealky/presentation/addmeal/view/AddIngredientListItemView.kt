package com.teammealky.mealky.presentation.addmeal.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.view.ShoppingListItemView
import kotlinx.android.synthetic.main.add_ingredient_list_item.view.*

@SuppressLint("ViewConstructor")
class AddIngredientListItemView @JvmOverloads constructor(
        context: Context,
        onClickListener: IngredientsAdapter.OnItemClickListener,
        fieldChangedListener: ShoppingListAdapter.FieldChangedListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : ShoppingListItemView(context, onClickListener, fieldChangedListener, attrs, defStyle) {

    override fun inflateLayout() {
        inflate(R.layout.add_ingredient_list_item, true)
        quantityTv.addTextChangedListener(this)
        addIngredientItemLayout.setOnClickListener(this)
        removeBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.removeBtn -> onClickListener.onItemClick(model!!)
            else -> removeBtn.performClick()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind() {
        model?.let {
            ingredientNameTv.text = model?.item?.name?.capitalize() + ":"
            quantityTv.setText(model?.item?.quantity?.toString(), TextView.BufferType.EDITABLE)
            unitTv.text = model?.item?.unit?.name
        }
    }
}