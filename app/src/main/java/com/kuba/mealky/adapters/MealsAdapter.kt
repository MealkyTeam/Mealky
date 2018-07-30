package com.kuba.mealky.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuba.mealky.R
import com.kuba.mealky.database.models.Meal
import com.kuba.mealky.util.toTime
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
        holder.view.prepTime.text = meal.prep_time.toTime()
        holder.view.preparation.text = meal.preparation
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
