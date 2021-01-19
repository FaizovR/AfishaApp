package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.faizovr.afisha.databinding.CategoryListItemViewHolderBinding
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: CategoryListItemViewHolderBinding by viewBinding(
        CategoryListItemViewHolderBinding::bind
    )

    fun bind(
        categoryDataView: CategoryDataView,
        onMenuClicked: (categoryDataView: CategoryDataView) -> Unit
    ) {
        with(binding) {
            textMenuItemTitle.text = categoryDataView.name
            root.setOnClickListener {
                onMenuClicked(categoryDataView)
            }
        }
    }
}