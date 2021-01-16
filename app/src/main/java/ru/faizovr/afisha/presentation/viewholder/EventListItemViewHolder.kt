package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.faizovr.afisha.R
import ru.faizovr.afisha.data.Date
import ru.faizovr.afisha.databinding.EventListItemViewHolderBinding
import ru.faizovr.afisha.domain.model.Dates
import ru.faizovr.afisha.domain.model.EventShortInfo
import java.text.SimpleDateFormat
import java.util.*

// TODO: 01.01.2021 перекинуть логику в Presenter/ Нарушенен принцип SOLID - S, принцип единственной отвественности

class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListItemViewHolderBinding =
        EventListItemViewHolderBinding.bind(itemView)

    private fun setEventImage(url: String) {
        Glide.with(itemView)
            .load(url)
            .placeholder(R.color.backgroundColor)
            .into(binding.imageViewEventHolder)
    }

    private fun setEventDate(datesList: List<Dates>) {
        val bindDate = bindDate(datesList)
        binding.textViewDateEvent.text = bindDate
        binding.textViewDateEvent.isVisible = bindDate.isNotEmpty()
    }

    private fun setEventTittle(title: String) {
        binding.textViewTittleEventHolder.text = title
    }

    private fun setEventDescription(description: String) {
        binding.textViewDescriptionEventHolder.text = description
    }

    private fun setOnEventClickListener(
        eventShortInfo: EventShortInfo,
        onEventClickListener: (eventShortInfo: EventShortInfo) -> Unit
    ) {
        binding.root.setOnClickListener { onEventClickListener(eventShortInfo) }
    }

    fun bind(
        eventShortInfo: EventShortInfo,
        onEventClickListener: (eventShortInfo: EventShortInfo) -> Unit
    ) {
        setEventImage(eventShortInfo.images.first().image)
        setEventDate(eventShortInfo.dates)
        setEventTittle(eventShortInfo.title)
        setEventDescription(eventShortInfo.description)
        setOnEventClickListener(eventShortInfo, onEventClickListener)
    }

    private fun bindDate(dates: List<Dates>): String {
        val currentTime = Date().getCurrentDate()
        val date = dates.find { it.start >= currentTime }?.start ?: return ""
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"))
        return simpleDateFormat.format(date)
    }
}