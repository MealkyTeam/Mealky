package com.teammealky.mealky.presentation.discover.view

import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.commons.extension.getResizedImageHeight
import com.teammealky.mealky.presentation.commons.extension.loadImage
import com.teammealky.mealky.presentation.discover.DiscoverPresenter

@Layout(R.layout.meal_card)
class MealCard(private val meal: Meal,
               private val swipeView: SwipePlaceHolderView, private val listener: DiscoverPresenter.SwipeListener) {

    @View(R.id.imageView)
    private lateinit var imageView: ImageView
    @View(R.id.mealName)
    private lateinit var mealName: TextView
    @View(R.id.cardView)
    private lateinit var cardView: MaterialCardView

    @Resolve
    private fun onResolved() {
        cardView.strokeColor = Color.BLACK
        cardView.strokeWidth = 1
        mealName.text = meal.name

        imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResizedImageHeight(ASPECT_RATIO)
        )
        val url = if (meal.images.isNotEmpty()) meal.images[0] else ""
        imageView.loadImage(url)
    }

    @SwipeOut
    private fun onSwipedOut() {
        listener.swipedLeft()
    }

    @SwipeIn
    private fun onSwipeIn() {
        listener.swipedRight()
    }

    companion object {
        private const val ASPECT_RATIO = 0.83f
    }
}