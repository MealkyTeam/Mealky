package com.teammealky.mealky.presentation.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.extension.loadImage
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.discover_fragment.*

class DiscoverFragment : BaseFragment<DiscoverPresenter, DiscoverPresenter.UI, DiscoverViewModel>(),
        DiscoverPresenter.UI, View.OnClickListener {

    override val vmClass = DiscoverViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.discover_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        presenter?.setup()

        likeImg.setOnClickListener(this)
        dislikeImg.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.likeImg -> presenter?.likeClicked()
            R.id.dislikeImg -> presenter?.dislikeClicked()
        }
    }

    override fun openItem(meal: Meal) {
        context?.let {
            Navigator.from(it as Navigator.Navigable).openMeal(meal)
        }
    }

    override fun setMeal(meal: Meal) {
        mealName.text = meal.name
        mealName.isVisible(true)
        imageViewBackground.loadImage(meal.images[0])
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}
