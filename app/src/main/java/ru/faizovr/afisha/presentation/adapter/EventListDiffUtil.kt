package ru.faizovr.afisha.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.afisha.presentation.model.EventListDataView

class EventListDiffUtil : DiffUtil.ItemCallback<EventListDataView>() {
    override fun areItemsTheSame(oldItem: EventListDataView, newItem: EventListDataView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: EventListDataView,
        newItem: EventListDataView
    ): Boolean =
        when {
            oldItem.date != newItem.date -> false
            oldItem.description != newItem.description -> false
            oldItem.imageUrl != newItem.imageUrl -> false
            oldItem.title != newItem.title -> false
            else -> true
        }
}
