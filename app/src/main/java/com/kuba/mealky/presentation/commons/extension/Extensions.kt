package com.kuba.mealky.presentation.commons.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuba.mealky.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

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