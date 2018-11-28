package com.teammealky.mealky.presentation.meal.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.teammealky.mealky.presentation.commons.extension.loadImage

class GalleryAdapter(
        private val context: Context,
        private val images: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ImageView(context)
        view.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getImageHeight()
        )
        view.scaleType= ImageView.ScaleType.FIT_XY
        bind(position, view)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun getImageHeight(): Int {
        val aspectRatio = ASPECT_RATIO

        return Math.round(getScreenWidth() * aspectRatio)
    }

    private fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels


    override fun getCount() = images.size

    override fun isViewFromObject(view: View, `object`: Any) = view === `object`

    private fun bind(position: Int, view: ImageView) {
        val item = images[position]
        view.loadImage(item)
    }

    companion object {
        private const val ASPECT_RATIO = 0.66f
    }

}
