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

class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListItemViewHolderBinding =
        EventListItemViewHolderBinding.bind(itemView)

    fun bind(eventShortInfo: EventShortInfo) {
        Glide.with(itemView)
            .load(eventShortInfo.images.first().image)
            .placeholder(R.color.backgroundColor)
            .into(binding.imageViewEventHolder)
        val bindDate = bindDate(eventShortInfo.dates)
        binding.textViewDateEvent.text = bindDate
        binding.textViewDateEvent.isVisible = bindDate.isNotEmpty()
        binding.textViewTittleEventHolder.text = eventShortInfo.title
        binding.textViewDescriptionEventHolder.text = eventShortInfo.description
    }

    private fun bindDate(dates: List<Dates>): String {
        val currentTime = Date().getCurrentDate()
        val date = dates.find { it.start >= currentTime }?.start ?: return ""
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"))
        return simpleDateFormat.format(date)
    }
}