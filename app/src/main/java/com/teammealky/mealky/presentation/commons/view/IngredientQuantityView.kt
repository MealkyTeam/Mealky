package com.teammealky.mealky.presentation.commons.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import kotlinx.android.synthetic.main.ingredient_quantity_view.view.*

class IngredientQuantityView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), TextWatcher {

    var model: IngredientViewModel? = null
        set(value) {
            field = value
            bind()
        }
    var listener: FieldChangedListener? = null

    init {
        inflate(R.layout.ingredient_quantity_view, true)
        quantityTv.addTextChangedListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind() {
        model?.let { vm ->
            ingredientNameTv.text = vm.item.name.capitalize() + ":"
            quantityTv.setText(vm.item.quantity.toString(), TextView.BufferType.EDITABLE)
            unitTv.text = vm.item.unit.name
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        val text = if (editable.isNullOrBlank()) "0" else editable.toString()
        val quantity = text.toDoubleOrNull() ?: 0.0
        model?.let { vm ->
            vm.item = vm.item.copy(quantity = quantity)
            listener?.fieldChanged(vm, quantity)
        }
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    companion object {
        interface FieldChangedListener {
            fun fieldChanged(model: IngredientViewModel, quantity: Double)
        }
    }
}