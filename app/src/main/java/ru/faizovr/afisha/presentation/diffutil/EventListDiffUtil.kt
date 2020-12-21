package ru.faizovr.afisha.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListDiffUtil : DiffUtil.ItemCallback<EventShortInfo>() {
    override fun areItemsTheSame(oldItem: EventShortInfo, newItem: EventShortInfo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EventShortInfo, newItem: EventShortInfo): Boolean =
        when {
            oldItem.description != newItem.description -> false
            oldItem.image != newItem.image -> false
            oldItem.slug != newItem.slug -> false
            oldItem.title != newItem.title -> false
            else -> true
        }
}
