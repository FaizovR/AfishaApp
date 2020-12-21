package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_list_footer_view_holder.view.*

class EventListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(state: LoadState, retry: () -> Unit) {
        val isLoading = state is LoadState.Loading
        itemView.button_footer_retry.isVisible = !isLoading
        itemView.text_view_footer_error.isVisible = !isLoading
        itemView.progress_bar_footer.isVisible = isLoading
        itemView.button_footer_retry.setOnClickListener {
            retry.invoke()
        }
    }
}