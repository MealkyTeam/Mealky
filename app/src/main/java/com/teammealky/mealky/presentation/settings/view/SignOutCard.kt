package com.teammealky.mealky.presentation.settings.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.commons.extension.dp2px
import com.teammealky.mealky.presentation.commons.extension.inflate
import com.teammealky.mealky.presentation.settings.SettingsPresenter
import kotlinx.android.synthetic.main.signout_layout.view.*

class SignOutCard @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle), View.OnClickListener {

    var listener: SettingsPresenter.SignOutListener? = null
        set(listener) {
            field = listener
            signOutBtn.setOnClickListener(this)
        }

    init {
        inflate(R.layout.signout_layout, true)
        radius = dp2px(8)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signOutBtn -> listener?.signOutBtnClicked()
        }
    }
}
