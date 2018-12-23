package com.teammealky.mealky.presentation.shoppinglist.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.shopping_list_item.view.*

@SuppressLint("ViewConstructor")
class ShoppingListItemView @JvmOverloads constructor(
        context: Context,
        private val listener: ShoppingListAdapter.ShoppingListItemListener,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), View.OnClickListener, TextWatcher {

    var model: ShoppingListItemViewModel? = null
        set(value) {
            field = value
            bind()
        }

    init {
        inflate(R.layout.shopping_list_item, true)
        shoppingListItemLayout.foreground = null
    }

    @SuppressLint("SetTextI18n")
    private fun bind() {
        model?.let {
            bullet.setImageResource(if (it.isGreyedOut) R.drawable.ic_bullet_grey_16dp else R.drawable.ic_bullet_black_16dp)
            checkbox.buttonDrawable =
                    ContextCompat.getDrawable(context,
                            if (it.isGreyedOut) R.drawable.checkbox_selector_grey else R.drawable.checkbox_selector_grey)
            checkbox.isChecked = it.isGreyedOut

            ingredientNameTv.text = model?.item?.name?.capitalize() + ":"
            quantityTv.setText(model?.item?.quantity?.toString(), TextView.BufferType.EDITABLE)
            quantityTv.addTextChangedListener(this)
            unitTv.text = model?.item?.unit?.name

            strikethrough.isVisible(it.isGreyedOut)

            ingredientNameTv.setTextColor(ContextCompat.getColor(context, if (it.isGreyedOut) R.color.colorPrimary else R.color.text_primary))
            quantityTv.setTextColor(ContextCompat.getColor(context, if (it.isGreyedOut) R.color.colorPrimary else R.color.text_primary))
            unitTv.setTextColor(ContextCompat.getColor(context, if (it.isGreyedOut) R.color.colorPrimary else R.color.text_primary))

            shoppingListItemLayout.setOnClickListener(this)
            checkbox.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.checkbox -> listener.onItemClick(model!!, checkbox.isChecked)
            else -> checkbox.performClick()
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        listener.fieldChanged(model!!, editable?.toString() ?: "")
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}