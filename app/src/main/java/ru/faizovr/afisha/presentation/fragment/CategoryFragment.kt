package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_category_list.*
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.activity.MainActivity
import ru.faizovr.afisha.presentation.adapter.CategoryAdapter
import ru.faizovr.afisha.presentation.contract.CategoryContract
import ru.faizovr.afisha.presentation.presenter.CategoryPresenter

class CategoryFragment : Fragment(R.layout.fragment_category_list), CategoryContract.View {

    private var categoryPresenter: CategoryContract.Presenter? = null

    private val onMenuClicked: (position: Int) -> Unit = { position: Int ->
        categoryPresenter?.onCategoryItemClickedForPosition(position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupPresenter()
        setupView()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.category_title)
    }

    private fun setupView() {
        recycler_view_category.adapter = CategoryAdapter(onMenuClicked)
        button_menu_retry.setOnClickListener { categoryPresenter?.onRetryClicked() }
    }

    private fun setupPresenter() {
        val app: App = requireActivity().application as App?
            ?: throw IllegalStateException("Fragment $this not attached to an app.")
        categoryPresenter = CategoryPresenter(this, app.repository)
    }

    override fun showNewFragment(category: Category) {
        val fragment: Fragment = EventListFragment.newInstance(category)
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    override fun setRetryButtonVisibility(isVisible: Boolean) {
        button_menu_retry.isVisible = isVisible
    }

    override fun setCategoryListVisibility(isVisible: Boolean) {
        recycler_view_category.isVisible = isVisible
    }

    override fun setErrorTextVisibility(isVisible: Boolean) {
        text_view_category_failed_message.isVisible = isVisible
    }

    override fun setProgressBarVisibility(isVisible: Boolean) {
        progress_bar_category.isVisible = isVisible
    }

    override fun showList(list: List<String>) {
        val adapter = recycler_view_category.adapter as CategoryAdapter?
        adapter?.updateList(list)
    }

    companion object {
        fun newInstance(): CategoryFragment {
            val args = Bundle()

            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}