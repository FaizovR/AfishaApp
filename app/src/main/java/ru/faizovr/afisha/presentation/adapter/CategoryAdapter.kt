package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.faizovr.afisha.databinding.CategoryListItemViewHolderBinding
import ru.faizovr.afisha.presentation.model.CategoryDataView
import ru.faizovr.afisha.presentation.viewholder.CategoryItemViewHolder

class CategoryAdapter(
    private val onCategoryItemClicked: (categoryDataView: CategoryDataView) -> Unit
) : ListAdapter<CategoryDataView, CategoryItemViewHolder>(CategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryListItemViewHolderBinding.inflate(layoutInflater, parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position), onCategoryItemClicked)
    }
}