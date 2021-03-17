package ru.faizovr.afisha.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.ViewHolderCategoryListItemBinding
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryItemViewHolder(
    private val binding: ViewHolderCategoryListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        categoryDataView: CategoryDataView,
        onMenuClicked: (categoryDataView: CategoryDataView) -> Unit
    ) =
        with(binding) {
            textMenuItemTitle.text = categoryDataView.name
            root.setOnClickListener { onMenuClicked(categoryDataView) }
        }
}