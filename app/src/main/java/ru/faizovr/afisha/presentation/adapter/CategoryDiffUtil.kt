package ru.faizovr.afisha.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.afisha.presentation.model.CategoryDataView

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryDataView>() {

    // TODO: 02.03.2021 добавить id
    override fun areItemsTheSame(oldItem: CategoryDataView, newItem: CategoryDataView): Boolean =
        oldItem.tag == newItem.tag &&
                oldItem.name == newItem.name


    override fun areContentsTheSame(oldItem: CategoryDataView, newItem: CategoryDataView): Boolean =
        oldItem.tag == newItem.tag &&
                oldItem.name == newItem.name
}