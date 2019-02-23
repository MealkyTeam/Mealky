package com.teammealky.mealky.presentation.meal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.meal.model.IngredientViewModel
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(var ingredients: List<IngredientViewModel> = emptyList(), private val listener: OnItemClickListener) :
        RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(model: IngredientViewModel)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = ingredients[position]
        val ingredient = model.item
        val checkbox = holder.view.checkbox

        holder.view.ingredientTv.text = ingredient.name.capitalize() + ": " + ingredient.quantity + " " + ingredient.unit.name
        checkbox.isChecked = model.isChecked
        holder.view.setOnClickListener {
            checkbox.performClick()
        }
        checkbox.setOnClickListener { listener.onItemClick(model) }
    }

    override fun getItemCount() = ingredients.size
}
