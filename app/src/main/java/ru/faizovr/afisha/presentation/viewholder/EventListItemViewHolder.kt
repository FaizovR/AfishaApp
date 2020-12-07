package ru.faizovr.afisha.presentation.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_list_item_view_holder.view.*
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.EventShortInfo

class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(eventShortInfo: EventShortInfo) {
        itemView.text_view_tittle_event_holder.text = eventShortInfo.title
        itemView.text_view_description_event_holder.text = eventShortInfo.description
    }

    companion object {
        fun create(parent: ViewGroup) : EventListItemViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item_view_holder, parent, false)
            return EventListItemViewHolder(view)
        }
    }
}