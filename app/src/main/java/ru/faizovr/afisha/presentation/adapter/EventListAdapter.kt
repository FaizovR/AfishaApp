package ru.faizovr.afisha.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.domain.model.EventShortInfo
import ru.faizovr.afisha.presentation.diffutil.EventListDiffUtil
import ru.faizovr.afisha.presentation.viewholder.EventListItemViewHolder

class EventListAdapter :
    PagingDataAdapter<EventShortInfo, RecyclerView.ViewHolder>(EventListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        EventListItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as EventListItemViewHolder).bind(it) }
    }
}