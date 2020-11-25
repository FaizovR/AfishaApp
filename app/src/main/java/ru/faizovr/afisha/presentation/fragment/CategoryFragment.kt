package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.adapter.CategoryAdapter
import ru.faizovr.afisha.presentation.contract.CategoryContract
import ru.faizovr.afisha.presentation.presenter.CategoryPresenter

class CategoryFragment : Fragment(R.layout.fragment_menu), CategoryContract.View {

    private var categoryPresenter : CategoryContract.Presenter? = null

    private val onMenuClicked: (position: Int) -> Unit = { position: Int ->
        categoryPresenter?.onCategoryItemClickedForPosition(position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPresenter()
        setupView()
    }

    private fun setupView() {
        recycler_view_category.adapter = CategoryAdapter(onMenuClicked)
    }

    private fun setupPresenter() {
        val app: App = activity?.application as App
        categoryPresenter = CategoryPresenter(this, app.repository)
        categoryPresenter?.init()
    }

    override fun showNewFragment(category: Category) {
        Toast.makeText(context, "Clicked ${category.name}", Toast.LENGTH_SHORT).show()
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
        val adapter = recycler_view_category.adapter as CategoryAdapter
        adapter.updateList(list)
    }
}