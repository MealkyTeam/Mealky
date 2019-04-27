package com.teammealky.mealky.presentation.shoppinglist.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.view.IngredientView
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.shopping_list_item.view.*

@SuppressLint("ViewConstructor")
open class ShoppingListItemView @JvmOverloads constructor(
        context: Context,
        val onClickListener: IngredientsAdapter.OnItemClickListener,
        private val fieldChangedListener: ShoppingListAdapter.FieldChangedListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : IngredientView(context, onClickListener, attrs, defStyle), TextWatcher {


    override fun inflateLayout() {
        inflate(R.layout.shopping_list_item, true)
        shoppingListItemLayout.foreground = null
        quantityTv.addTextChangedListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun bind() {
        model?.let {
            bullet.setImageResource(if (it.isChecked) R.drawable.ic_bullet_grey_16dp else R.drawable.ic_bullet_black_16dp)
            checkbox.buttonDrawable =
                    ContextCompat.getDrawable(context,
                            if (it.isChecked) R.drawable.checkbox_selector_grey else R.drawable.checkbox_selector_grey)
            checkbox.isChecked = it.isChecked

            ingredientNameTv.text = model?.item?.name?.capitalize() + ":"
            quantityTv.setText(model?.item?.quantity?.toString(), TextView.BufferType.EDITABLE)
            unitTv.text = model?.item?.unit?.name

            strikethrough.isVisible(it.isChecked)

            ingredientNameTv.setTextColor(ContextCompat.getColor(context, if (it.isChecked) R.color.colorPrimary else R.color.text_primary))
            quantityTv.setTextColor(ContextCompat.getColor(context, if (it.isChecked) R.color.colorPrimary else R.color.text_primary))
            unitTv.setTextColor(ContextCompat.getColor(context, if (it.isChecked) R.color.colorPrimary else R.color.text_primary))

            shoppingListItemLayout.setOnClickListener(this)
            checkbox.setOnClickListener(this)
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        val text = if (editable.isNullOrBlank()) "0" else editable.toString()
        val quantity = text.toDoubleOrNull() ?: 0.0
        model?.let { vm ->
            vm.item = vm.item.copy(quantity = quantity)
            fieldChangedListener.fieldChanged(vm, quantity)
        }
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}