package ru.faizovr.afisha.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.faizovr.afisha.presentation.viewholder.EventListFooterViewHolder

class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<EventListFooterViewHolder>() {

    override fun onBindViewHolder(holder: EventListFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): EventListFooterViewHolder =
        EventListFooterViewHolder.create(parent)
}