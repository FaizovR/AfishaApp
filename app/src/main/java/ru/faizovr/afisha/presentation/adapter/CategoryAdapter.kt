package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.R
import ru.faizovr.afisha.presentation.model.CategoryDataView
import ru.faizovr.afisha.presentation.viewholder.CategoryItemViewHolder

class CategoryAdapter(private val onCategoryItemClicked: (categoryDataView: CategoryDataView) -> Unit) :
    RecyclerView.Adapter<CategoryItemViewHolder>() {

    private var categoryList: List<CategoryDataView> = listOf()

    fun updateList(newList: List<CategoryDataView>) {
        categoryList = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item_view_holder, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun getItemCount(): Int =
        categoryList.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(categoryList[position], onCategoryItemClicked)
    }
}