package ru.faizovr.afisha.presentation.viewholder

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.EventListItemViewHolderBinding
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListItemViewHolderBinding =
        EventListItemViewHolderBinding.bind(itemView)

    @SuppressLint("ResourceType")
    fun bind(eventShortInfo: EventShortInfo) {
        Log.d("TAG", "bind: ${eventShortInfo.images}")
        Glide.with(itemView)
            .load(eventShortInfo.images.first().image)
            .placeholder(R.color.backgroundColor)
            .into(binding.imageViewEventHolder)
        binding.textViewTittleEventHolder.text = eventShortInfo.title
        binding.textViewDescriptionEventHolder.text = eventShortInfo.description
    }
}