package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.CategoryListItemViewHolderBinding
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: CategoryListItemViewHolderBinding =
        CategoryListItemViewHolderBinding.bind(itemView)

    fun bind(
        categoryDataView: CategoryDataView,
        onMenuClicked: (categoryDataView: CategoryDataView) -> Unit
    ) {
        binding.textMenuItemTitle.text = categoryDataView.name
        itemView.setOnClickListener {
            onMenuClicked(categoryDataView)
        }
    }
}