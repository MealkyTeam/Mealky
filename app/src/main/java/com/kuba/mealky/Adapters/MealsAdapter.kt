package com.kuba.mealky.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuba.mealky.Database.Entities.MealData
import com.kuba.mealky.R


class MealsAdapter(private val meals: List<MealData>, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: MealData)
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bindListener(item: MealData, listener: OnItemClickListener) {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    listener.onItemClick(item)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.meal_text_view, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = meals[position].toString()
        holder.bindListener(meals[position], listener)
    }

    override fun getItemCount() = meals.size
}
