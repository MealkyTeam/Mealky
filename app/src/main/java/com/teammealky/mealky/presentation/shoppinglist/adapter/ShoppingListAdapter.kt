package com.teammealky.mealky.presentation.shoppinglist.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.teammealky.mealky.presentation.meal.adapter.IngredientsAdapter
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import com.teammealky.mealky.presentation.shoppinglist.view.ShoppingListItemView

class ShoppingListAdapter(models: List<IngredientViewModel> = emptyList(),
                          private val fieldChangedListener: FieldChangedListener,
                          onClickListener: IngredientsAdapter.OnItemClickListener)
    : IngredientsAdapter(models, onClickListener) {

    interface FieldChangedListener {
        fun fieldChanged(model: IngredientViewModel, quantity: Double)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(ShoppingListItemView(parent.context, listener, fieldChangedListener))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = models[position]
        (holder.view as ShoppingListItemView).model = model
    }

    fun fillAdapter(models: List<IngredientViewModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.models = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = models.size

}