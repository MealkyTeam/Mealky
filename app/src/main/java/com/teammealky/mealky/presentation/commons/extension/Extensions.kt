package com.teammealky.mealky.presentation.commons.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teammealky.mealky.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.util.*

fun Int.toTime(): String {
    val originalValue = this
    val numOfHours = (originalValue / 60)
    val numOfMinutes = originalValue - numOfHours * 60

    return when {
        numOfHours > 0 && numOfMinutes > 0 -> "${numOfHours}h ${numOfMinutes}m"
        numOfHours > 0 && numOfMinutes == 0 -> "${numOfHours}h"
        numOfHours == 0 && numOfMinutes > 0 -> "${numOfMinutes}m"
        else -> ""
    }
}

fun BottomNavigationView.clearSelection() {
    for (i in 0 until menu.size()) {
        menu.getItem(i).apply {
            isCheckable = false
            isChecked = false
            isCheckable = true
        }
    }
}

fun BottomNavigationView.markAsSelected(position: Int) {
    clearSelection()
    menu.getItem(position).isChecked = true
}

fun ImageView.loadImage(url: String, transformation: Transformation? = null,
                        callback: Callback? = null,
                        placeholder: Drawable? = null,
                        fit: Boolean = false) {
    if (url.isEmpty()) return
    tag = url

    val picasso = Picasso
            .get()
            .load(url)
            .config(Bitmap.Config.RGB_565)
            .error(R.drawable.ic_error_outline_white_large)

    if (null != placeholder) picasso.placeholder(placeholder)
    if (fit) picasso.fit()
    if (transformation != null) picasso.transform(transformation)

    picasso.into(this, callback)
}

fun View.isVisible(isVisible: Boolean) {
    when (isVisible) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
}

fun genRandomIntExcept(start: Int, end: Int, excluded: List<Int>): Int {
    val rand = Random()
    val range = end - start

    var random = rand.nextInt(range)
    while (excluded.contains(random)) {
        random = rand.nextInt(range)
    }
    return random
}