package com.teammealky.mealky.presentation.shoppinglist.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import com.teammealky.mealky.presentation.shoppinglist.view.ShoppingListItemView

class ShoppingListAdapter(var models: List<ShoppingListItemViewModel> = emptyList(), private val listener: ShoppingListItemListener) :
        RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    interface ShoppingListItemListener {
        fun onItemClick(model: ShoppingListItemViewModel, isChecked: Boolean)
        fun fieldChanged(model: ShoppingListItemViewModel, text: String)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(ShoppingListItemView(parent.context, listener))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = models[position]
        (holder.view as ShoppingListItemView).model = model
    }

    fun fillAdapter(models: List<ShoppingListItemViewModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.models = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = models.size

}
