package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.CategoryListItemViewHolderBinding

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: CategoryListItemViewHolderBinding =
        CategoryListItemViewHolderBinding.bind(itemView)

    fun bind(message: String, onMenuClicked: (position: Int) -> Unit) {
        binding.textMenuItemTitle.text = message
        itemView.setOnClickListener {
            onMenuClicked(bindingAdapterPosition)
        }
    }
}