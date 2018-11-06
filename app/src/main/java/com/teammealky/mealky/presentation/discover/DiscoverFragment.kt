package com.teammealky.mealky.presentation.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.shoppinglist.ShoppingListPresenter

class DiscoverFragment : BaseFragment<DiscoverPresenter, DiscoverPresenter.UI, DiscoverViewModel>(), ShoppingListPresenter.UI {

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

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}
