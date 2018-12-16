package com.teammealky.mealky.presentation.meal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.R.string.ingredients
import com.teammealky.mealky.domain.model.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(var ingredients: List<Ingredient> = emptyList(), private val listener: OnItemClickListener) :
        RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Ingredient, isChecked: Boolean)
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
        val ingredient = ingredients[position]
        val checkbox = holder.view.checkbox

        holder.view.ingredientTv.text = ingredient.name.capitalize() + ": " + ingredient.quantity + " " + ingredient.unit.name
        holder.view.setOnClickListener {
            checkbox.performClick()
        }
        checkbox.setOnClickListener { listener.onItemClick(ingredient, checkbox.isChecked) }
    }

    fun fillAdapter(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.ingredients = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = ingredients.size
}
