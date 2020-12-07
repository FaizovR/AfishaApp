package ru.faizovr.afisha.presentation.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_list_footer_view_holder.view.*
import ru.faizovr.afisha.R
                                                                                
class EventListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(state: LoadState, retry: () -> Unit) {
        itemView.button_footer_retry.isVisible = state !is LoadState.Loading
        itemView.text_view_footer_error.isVisible = state !is LoadState.Loading
        itemView.progress_bar_footer.isVisible = state is LoadState.Loading

        itemView.button_footer_retry.setOnClickListener {
            retry.invoke()
        }
    }

    companion object {
        fun create(parent: ViewGroup) : EventListFooterViewHolder {
         val view: View = LayoutInflater.from(parent.context).inflate(R.layout.event_list_footer_view_holder, parent, false)
         return EventListFooterViewHolder(view)
        }
    }
}