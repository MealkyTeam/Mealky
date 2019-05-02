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
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.discover_fragment.*
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.teammealky.mealky.presentation.discover.view.MealCard
import com.teammealky.mealky.presentation.commons.extension.getDisplaySize


class DiscoverFragment : BaseFragment<DiscoverPresenter, DiscoverPresenter.UI, DiscoverViewModel>(),
        DiscoverPresenter.UI, View.OnClickListener, DiscoverPresenter.SwipeListener {

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
        likeImg.setOnClickListener(this)
        dislikeImg.setOnClickListener(this)

        setupCardView()
        presenter?.firstRequest()
    }

    private fun setupCardView() {
        val displaySize = getDisplaySize(activity?.windowManager!!)
        val width = displaySize.x
        val height = displaySize.y - resources.getDimension(R.dimen.bottomBarHeight)

        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(3)
                .setWidthSwipeDistFactor(8f)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingLeft(20)
                        .setRelativeScale(0.01f)
                        .setViewWidth(width)
                        .setViewHeight(height.toInt())
                )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.likeImg -> swipedRight()
            R.id.dislikeImg -> swipeMeal()
        }
    }

    override fun openItem(meal: Meal) {
        Navigator.from(context as Navigator.Navigable).openMeal(meal)
    }

    override fun setMeals(meals: List<Meal>) {
        for (meal in meals) {
            swipeView.addView(MealCard(meal, this))
        }
    }

    override fun swipeMeal() {
        swipeView.doSwipe(false)
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
        likeImg.isClickable = !isLoading
        dislikeImg.isClickable = !isLoading
    }

    override fun swipedLeft() {
        presenter?.swipedLeft()
    }

    override fun swipedRight() {
        presenter?.swipedRight()
    }
}
