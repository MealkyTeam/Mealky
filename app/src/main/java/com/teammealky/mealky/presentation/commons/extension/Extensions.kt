package com.teammealky.mealky.presentation.commons.extension

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teammealky.mealky.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.util.*
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager

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

fun ViewGroup.inflate(
        layoutId: Int, attachToRoot: Boolean = false
): View = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

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
            .error(R.drawable.broken_image)

    if (null != placeholder) picasso.placeholder(placeholder)
    else picasso.placeholder(R.color.colorPrimary)
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
    if (range <= excluded.size)
        return 0
    var random = rand.nextInt(range)
    while (excluded.contains(random)) {
        random = rand.nextInt(range)
    }
    return random
}

fun dp2px(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density

fun getDisplaySize(windowManager: WindowManager): Point {
    return try {
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
    } catch (e: Exception) {
        e.printStackTrace()
        Point(0, 0)
    }
}

fun getResizedImageHeight(aspectRatio: Float): Int {
    return Math.round(getScreenWidth() * aspectRatio)
}

fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels
