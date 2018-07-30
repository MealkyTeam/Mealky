package com.kuba.mealky.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.R
import kotlinx.android.synthetic.main.meal_item.view.*


class MealsAdapter(var meals: MutableList<MealData>, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: MealData)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindListener(item: MealData, listener: OnItemClickListener) {
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
        holder.view.mealTextView.text = meals[position].toString()
        holder.bindListener(meals[position], listener)
    }

    override fun getItemCount() = meals.size

    fun getItem(i: Int): MealData? {
        return meals[i]
    }

    fun removeAt(position: Int) {
        meals.removeAt(position)
        notifyItemRemoved(position)
    }
}
