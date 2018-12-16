package com.teammealky.mealky.presentation.shoppinglist.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.ingredient_item.view.*
import timber.log.Timber

class ShoppingListItemView @JvmOverloads constructor(
        context: Context,
        val listener: ShoppingListAdapter.OnItemClickListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    var model: ShoppingListItemViewModel? = null
        set(value) {
            field = value
            bind()

        }

    init {
        inflate(R.layout.ingredient_item, true)
        ingredientItemLayout.foreground = null
    }

    @SuppressLint("SetTextI18n")
    private fun bind() {
        model?.let {
            bullet.setImageResource(if (it.isGreyedOut) R.drawable.ic_bullet_grey_16dp else R.drawable.ic_bullet_black_16dp)
            checkbox.buttonDrawable =
                    ContextCompat.getDrawable(context,
                            if (it.isGreyedOut) R.drawable.checkbox_selector_grey else R.drawable.checkbox_selector_grey)
            checkbox.isChecked = it.isGreyedOut

            ingredientTv.text = model?.item?.name?.capitalize() + ": " + model?.item?.quantity + " " + model?.item?.unit?.name
            ingredientTv.paintFlags = (if (it.isGreyedOut) Paint.STRIKE_THRU_TEXT_FLAG else (ingredientTv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()))
            ingredientTv.setTextColor(ContextCompat.getColor(context, if (it.isGreyedOut) R.color.colorPrimary else R.color.text_primary))

            ingredientItemLayout.setOnClickListener {
                Timber.d("KUBA Method:bind *****  *****")
                checkbox.performClick()
            }
            checkbox.setOnClickListener { listener.onItemClick(model!!, checkbox.isChecked) }
        }
    }

}