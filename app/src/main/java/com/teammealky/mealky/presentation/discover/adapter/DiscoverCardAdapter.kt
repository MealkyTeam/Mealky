package com.teammealky.mealky.presentation.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.extension.loadImage
import kotlinx.android.synthetic.main.meal_card.view.*

class DiscoverCardAdapter(val meals: MutableList<Meal>) :
        RecyclerView.Adapter<DiscoverCardAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.meal_card, parent, false)
        val holder = ViewHolder(layout)
        holder.view.mealName.isVisible(true)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]

        holder.view.mealName.text = meal.name

        val url = if (meal.images.isNotEmpty()) meal.images[0] else ""
        holder.view.imageView.loadImage(url)
    }

    fun setMeals(meals: List<Meal>) {
        this.meals.addAll(meals)

        notifyDataSetChanged()
    }

    override fun getItemCount() = meals.size
}
