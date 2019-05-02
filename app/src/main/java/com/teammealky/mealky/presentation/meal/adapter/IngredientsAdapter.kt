package com.teammealky.mealky.presentation.meal.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import com.teammealky.mealky.presentation.meal.view.IngredientView

open class IngredientsAdapter(var models: List<IngredientViewModel> = emptyList(), val listener: OnItemClickListener) :
        RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(model: IngredientViewModel)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(IngredientView(parent.context, listener))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = models[position]
        (holder.view as IngredientView).model = model
    }

    override fun getItemCount() = models.size
}
