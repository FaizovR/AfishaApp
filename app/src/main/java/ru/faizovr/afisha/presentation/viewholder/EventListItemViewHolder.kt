package ru.faizovr.afisha.presentation.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.ViewHolderEventListItemBinding
import ru.faizovr.afisha.presentation.model.EventListDataView

class EventListItemViewHolder(
    private val binding: ViewHolderEventListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private fun setEventImage(url: String) {
        Glide.with(itemView)
            .load(url)
            .placeholder(R.color.backgroundColor)
            .into(binding.imageViewEventHolder)
    }

    // TODO: 02.03.2021 вынести логику
    private fun setEventDate(dateString: String) {
        binding.textViewDateEvent.text = dateString
        binding.textViewDateEvent.isVisible = dateString.isNotEmpty()
    }

    private fun setEventTittle(title: String) {
        binding.textViewTittleEventHolder.text = title
    }

    private fun setEventDescription(description: String) {
        binding.textViewDescriptionEventHolder.text = description
    }

    private fun setOnEventClickListener(
        eventListDataView: EventListDataView,
        onEventClickListener: (eventListDataView: EventListDataView) -> Unit
    ) {
        binding.root.setOnClickListener { onEventClickListener(eventListDataView) }
    }

    fun bind(
        eventListDataView: EventListDataView,
        onEventClickListener: (eventListDataView: EventListDataView) -> Unit
    ) {
        setEventImage(eventListDataView.imageUrl)
        setEventDate(eventListDataView.date)
        setEventTittle(eventListDataView.title)
        setEventDescription(eventListDataView.description)
        setOnEventClickListener(eventListDataView, onEventClickListener)
    }
}