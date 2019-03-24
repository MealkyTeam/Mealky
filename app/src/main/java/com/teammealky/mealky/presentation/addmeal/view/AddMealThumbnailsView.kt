package com.teammealky.mealky.presentation.addmeal.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.addmeal.model.ThumbnailImage
import kotlinx.android.synthetic.main.add_meal_image.view.*

class AddMealThumbnailsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : HorizontalScrollView(context, attrs, defStyle), View.OnClickListener {

    private var containerView: LinearLayout = LinearLayout(context)
    private val photoViews = ArrayList<View>(0)
    private val photos = ArrayList<ThumbnailImage>(0)
    var deleteListener: OnImageDeleteListener? = null

    init {
        containerView.orientation = LinearLayout.HORIZONTAL
        containerView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(containerView)
    }

    fun updateQueue(item: ThumbnailImage) {
        val updateIndex = photos.indexOf(item)
        photoViews.getOrNull(updateIndex)?.let {
            val imageView: ImageView = it.thumbnailIv
            imageView.visibility = View.VISIBLE
            loadImageWithFit(imageView, item.uri)
        }
    }

    fun showQueue(items: List<ThumbnailImage>) {
        containerView.removeAllViews()
        photoViews.clear()
        photos.clear()

        if (items.isNotEmpty()) {
            photos.addAll(items)
            items.forEach { addPhoto(it) }
            postDelayed({ fullScroll(HorizontalScrollView.FOCUS_RIGHT) }, 1000)
        } else {
            visibility = View.GONE
        }
    }

    private fun addPhoto(photo: ThumbnailImage) {
        if (visibility == View.GONE) visibility = View.VISIBLE

        val thumbView = LayoutInflater.from(context).inflate(R.layout.add_meal_image, containerView, false)
        val imageView = thumbView.thumbnailIv
        val deleteBtn = thumbView.thumbnailDelete

        loadImageWithFit(imageView, photo.uri)
        deleteBtn.tag = photo.id
        deleteBtn.setOnClickListener(this)

        photoViews.add(thumbView)
        containerView.addView(thumbView)
    }

    override fun onClick(v: View) {
        if (null == deleteListener) return
        v.tag?.let { tag ->
            val id = tag as Int
            val photo = photos.firstOrNull { it.id == id }
            if (null != photo) deleteListener?.onImageDelete(photo)
        }
    }

    private fun loadImageWithFit(target: ImageView, filePath: String) {
        val picasso = Picasso
                .get()
                .load(filePath)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .fit()
                .error(R.drawable.broken_image)
        picasso.into(target)
    }

    interface OnImageDeleteListener {
        fun onImageDelete(image: ThumbnailImage)
    }
}
