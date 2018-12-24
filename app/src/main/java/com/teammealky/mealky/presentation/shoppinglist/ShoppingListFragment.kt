package com.teammealky.mealky.presentation.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teammealky.mealky.R
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseFragment
import kotlinx.android.synthetic.main.shopping_list_fragment.*
import kotlinx.android.synthetic.main.shopping_toolbar.*
import kotlinx.android.synthetic.main.shopping_toolbar.view.*
import com.google.android.material.snackbar.Snackbar
import com.teammealky.mealky.presentation.commons.extension.isVisible
import com.teammealky.mealky.presentation.shoppinglist.adapter.ShoppingListAdapter
import com.teammealky.mealky.presentation.shoppinglist.model.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.empty_item.*

class ShoppingListFragment : BaseFragment<ShoppingListPresenter, ShoppingListPresenter.UI, ShoppingListViewModel>(), ShoppingListPresenter.UI,
        ShoppingListAdapter.ShoppingListItemListener, View.OnClickListener {

    override val vmClass = ShoppingListViewModel::class.java
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.shopping_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        presenter?.setupPresenter()

        shoppingListToolbar.clearListBtn.setOnClickListener(this)
    }

    override fun setupRecyclerView(ingredients: List<ShoppingListItemViewModel>) {
        layoutManager = LinearLayoutManager(context)
        adapter = ShoppingListAdapter(ingredients, this)

        shopListRv.adapter = adapter
        shopListRv.setHasFixedSize(true)
        shopListRv.layoutManager = layoutManager
    }

    override fun onItemClick(model: ShoppingListItemViewModel, isChecked: Boolean) {
        presenter?.onItemClicked(model, isChecked)
    }

    override fun showSnackbar() {
        Snackbar.make(shopListFrameLayout, getString(R.string.shopping_list_snackbar_text), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.click).toUpperCase(), this)
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                            presenter?.snackbarDismissed()

                        super.onDismissed(transientBottomBar, event)
                    }
                })
                .show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.clearListBtn -> presenter?.onClearListBtnClicked()
            R.id.snackbar_action -> presenter?.onSnackbarActionClicked()
        }
    }

    override fun showToast(succeeded: Boolean) {
        val message = if (succeeded) getString(R.string.list_cleared) else getString(R.string.something_went_wrong)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun fillList(ingredients: List<ShoppingListItemViewModel>) {
        adapter.fillAdapter(ingredients)
    }

    override fun clearList() {
        adapter.clearAdapter()
    }

    override fun enableClearListBtn(isEnabled: Boolean) {
        shoppingListToolbar.clearListBtn.isEnabled = isEnabled
    }

    override fun showEmptyView(isEnabled: Boolean) {
        emptyItemLayout.isVisible(isEnabled)
    }

    override fun fieldChanged(model: ShoppingListItemViewModel, text: String) {
        presenter?.fieldChanged(model, text)
    }

}
