package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.EventListItemViewHolderBinding
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListItemViewHolderBinding =
        EventListItemViewHolderBinding.bind(itemView)

    fun bind(eventShortInfo: EventShortInfo) {
        binding.textViewTittleEventHolder.text = eventShortInfo.title
        binding.textViewDescriptionEventHolder.text = eventShortInfo.description
    }
}