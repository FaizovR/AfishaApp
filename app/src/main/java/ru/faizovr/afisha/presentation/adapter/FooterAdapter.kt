package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.faizovr.afisha.R
import ru.faizovr.afisha.presentation.viewholder.EventListFooterViewHolder

class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<EventListFooterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): EventListFooterViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_footer_view_holder, parent, false)
        return EventListFooterViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
}