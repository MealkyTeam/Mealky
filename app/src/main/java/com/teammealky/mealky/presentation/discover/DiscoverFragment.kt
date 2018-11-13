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
import com.teammealky.mealky.presentation.discover.adapter.DiscoverCardAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import timber.log.Timber


class DiscoverFragment : BaseFragment<DiscoverPresenter, DiscoverPresenter.UI, DiscoverViewModel>(),
        DiscoverPresenter.UI, View.OnClickListener, CardStackListener {

    override val vmClass = DiscoverViewModel::class.java
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: DiscoverCardAdapter

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
        presenter?.firstRequest()

        likeImg.setOnClickListener(this)
        dislikeImg.setOnClickListener(this)

        setupCardView()
    }

    private fun setupCardView() {
        manager = CardStackLayoutManager(context, this)
        manager.setStackFrom(StackFrom.None)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        adapter = DiscoverCardAdapter(mutableListOf())
        cards.layoutManager = manager
        cards.adapter = adapter
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.likeImg -> presenter?.likeClicked()
            R.id.dislikeImg -> presenter?.dislikeClicked(false)
        }
    }

    override fun openItem(meal: Meal) {
        context?.let {
            Navigator.from(it as Navigator.Navigable).openMeal(meal)
        }
    }

    override fun setMeals(meals: List<Meal>) {
        adapter.setMeals(meals)
    }

    override fun swipeMeal() {
        cards.swipe()
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
        likeImg.isClickable=!isLoading
        dislikeImg.isClickable=!isLoading
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Timber.e("FunName:onCardDragging ***** *****")
    }

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Left -> presenter?.likeClicked()
            Direction.Right -> presenter?.dislikeClicked(true)
            else -> {
            }
        }
        Timber.e("FunName:onCardSwiped ***** *****")
    }

    override fun onCardCanceled() {
        Timber.e("FunName:onCardCanceled ***** *****")
    }

    override fun onCardRewound() {
        Timber.e("FunName:onCardRewound ***** *****")
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}
