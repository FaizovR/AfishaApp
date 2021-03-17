package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.faizovr.afisha.databinding.ViewHolderEventListItemBinding
import ru.faizovr.afisha.presentation.model.EventListDataView
import ru.faizovr.afisha.presentation.viewholder.EventListItemViewHolder

class EventListAdapter(private val onEventClickListener: (eventListDataView: EventListDataView) -> Unit) :
    PagingDataAdapter<EventListDataView, EventListItemViewHolder>(EventListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderEventListItemBinding.inflate(layoutInflater, parent, false)
        return EventListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onEventClickListener) }
    }
}