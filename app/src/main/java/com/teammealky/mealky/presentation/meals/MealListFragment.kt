package com.teammealky.mealky.presentation.meals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.domain.model.Meal
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.Navigator
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.commons.listener.PaginationScrollListener
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import com.teammealky.mealky.presentation.meals.MealListPresenter.Companion.LIMIT
import com.teammealky.mealky.presentation.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.empty_item.*
import kotlinx.android.synthetic.main.empty_item.view.*
import kotlinx.android.synthetic.main.meals_fragment.*
import kotlinx.android.synthetic.main.search_toolbar.*
import android.os.Parcelable
import com.teammealky.mealky.presentation.commons.Navigator.Companion.INVALIDATE_LIST_KEY
import com.teammealky.mealky.presentation.commons.listener.ReSelectTabListener

class MealListFragment : BaseFragment<MealListPresenter, MealListPresenter.UI, MealListViewModel>(),
        MealListPresenter.UI, MealsAdapter.OnItemClickListener,
        TextWatcher, TextView.OnEditorActionListener, View.OnClickListener, ReSelectTabListener {

    override val vmClass = MealListViewModel::class.java

    private lateinit var adapter: MealsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.meals_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.firstRequest()
        setupSearch()
        setupRecyclerView()
    }

    override fun showEmptyView(isVisible: Boolean, query: String) {
        emptyItemLayout.isVisible(isVisible)
        emptyItemLayout.emptyItemTv.text = getString(R.string.empty_search_for, query)
    }

    private fun setupSearch() {
        addMealBtn.setOnClickListener(this)
        searchEditText.addTextChangedListener(this)
        searchEditText.setOnEditorActionListener(this)
    }

    override fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        adapter = MealsAdapter(listener = this)

        mealListRv.adapter = adapter
        mealListRv.setHasFixedSize(true)
        mealListRv.layoutManager = layoutManager

        mealListRv.addOnScrollListener(object : PaginationScrollListener(layoutManager as LinearLayoutManager, LIMIT - 2) {
            override fun isLastPage(): Boolean {
                return presenter?.shouldStopLoadMore() ?: true
            }

            override fun isLoading(): Boolean {
                return presenter?.isLoading ?: false
            }

            override fun loadMoreItems() {
                presenter?.isLoading = true
                presenter?.loadMore()
            }
        })
    }

    override fun fillList(meals: List<Meal>) {
        adapter.addItems(meals)
    }

    override fun clearList() {
        adapter.clearList()
    }

    override fun scrollToSaved(savedRecyclerView: Parcelable?) {
        layoutManager.onRestoreInstanceState(savedRecyclerView)
    }

    override fun isLoading(isLoading: Boolean) {
        progressBar.isVisible(isLoading)
    }

    override fun openItem(meal: Meal) {
        Navigator.from(context as Navigator.Navigable).openMeal(meal)
    }

    override fun onItemClick(item: Meal) {
        presenter?.onItemClicked(item)
    }

    override fun scrollToTop(animate: Boolean) {
        if (animate)
            mealListRv.smoothScrollToPosition(0)
        else
            mealListRv.scrollToPosition(0)
    }

    override fun onPause() {
        presenter?.onPaused(layoutManager.onSaveInstanceState())
        super.onPause()
    }

    override fun afterTextChanged(editable: Editable?) {
        if (presenter?.currentQuery == searchEditText.text.toString()) {
            return
        }
        presenter?.currentQuery = searchEditText.text.toString()
        presenter?.search()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null && actionId == EditorInfo.IME_ACTION_DONE) {
            presenter?.search()
            return true
        }

        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addMealBtn -> presenter?.onAddMealBtnClicked()
        }
    }

    override fun onReSelected() {
        presenter?.fragmentReselected()
    }

    fun onNewArguments(arguments: Bundle?) {
        if (null == arguments) return

        val invalidateList = arguments.getBoolean(INVALIDATE_LIST_KEY)
        presenter?.invalidateRepository(invalidateList)
    }

    override fun openAddMeal() {
        Navigator.from(context as Navigator.Navigable).openActivity(Navigator.ACTIVITY_ADD_MEAL)
    }

    override fun clearSearchText() {
        searchEditText.setText("")
    }

    companion object {
        fun newInstance(invalidateList: Boolean): MealListFragment {
            val fragment = MealListFragment()
            val bundle = Bundle()
            bundle.putBoolean(INVALIDATE_LIST_KEY, invalidateList)
            fragment.arguments = bundle

            return fragment
        }
    }
}
