package com.teammealky.mealky.presentation.addMeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment


class AddMealFragment : BaseFragment<AddMealPresenter, AddMealPresenter.UI, AddMealViewModel>(),
        AddMealPresenter.UI{

    override val vmClass = AddMealViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.add_meal_fragment, container, false)

}
