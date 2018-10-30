package com.kuba.mealky.presentation.commons.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.kuba.mealky.R

/**
 * By kitek www.github.com/kitek
 * ImageView that keeps aspect ratio changing the height
 */
class AspectRatioImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    @Suppress("MemberVisibilityCanBePrivate")
    var measureOnceListener: OnMeasureListener? = null
    private var widthRatio = 1f

    init {
        attrs?.let {
            var a: TypedArray? = null
            try {
                a = context.theme.obtainStyledAttributes(it, R.styleable.AspectRatioImageView, 0, 0)
                widthRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, 1f)
            } catch (ignored: Exception) {
            } finally {
                a?.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = Math.round(width * widthRatio)
        setMeasuredDimension(width, height)

        measureOnceListener?.onViewMeasure(width, height)
        measureOnceListener = null
    }

    interface OnMeasureListener {
        fun onViewMeasure(width: Int, height: Int)
    }
}