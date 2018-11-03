package com.teammealky.mealky.presentation.meals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.commons.extension.loadImage
import kotlinx.android.synthetic.main.meal_item.view.*

class MealsAdapter(var meals: MutableList<Meal>, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Meal)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindListener(item: Meal, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.meal_item, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.view.mealName.text = meal.name
        holder.view.preparation.text = meal.preparation
        val url = if(meal.images.isNotEmpty()) meal.images[0] else ""
        holder.view.imageView.loadImage(url)
        holder.bindListener(meals[position], listener)
    }

    override fun getItemCount() = meals.size

    fun getItem(i: Int): Meal? {
        return meals[i]
    }

    fun removeAt(position: Int) {
        meals.removeAt(position)
        notifyItemRemoved(position)
    }
}
