package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.diffutil.EventListDiffUtil
import ru.faizovr.afisha.presentation.viewholder.EventListItemViewHolder

class EventListAdapter(private val onEventClickListener: (eventShortInfo: EventShortInfo) -> Unit) :
    PagingDataAdapter<EventShortInfo, EventListItemViewHolder>(EventListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item_view_holder, parent, false)
        return EventListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onEventClickListener) }
    }
}