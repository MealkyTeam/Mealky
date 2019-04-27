package com.teammealky.mealky.presentation.addmeal.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.teammealky.mealky.presentation.addmeal.view.AddIngredientListItemView
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.view.ShoppingListItemView

class AddedIngredientsAdapter(models: List<IngredientViewModel> = emptyList(),
                              fieldChangedListener: FieldChangedListener,
                              onClickListener: OnItemClickListener)
    : ShoppingListAdapter(models, fieldChangedListener, onClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(AddIngredientListItemView(parent.context, listener, fieldChangedListener))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = models[position]
        (holder.view as ShoppingListItemView).model = model
    }

    override fun getItemCount() = models.size
}