package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.faizovr.afisha.databinding.ViewHolderEventListFooterBinding
import ru.faizovr.afisha.presentation.viewholder.EventListFooterViewHolder

class FooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<EventListFooterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): EventListFooterViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderEventListFooterBinding.inflate(layoutInflater, parent, false)
        return EventListFooterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListFooterViewHolder, loadState: LoadState) =
        holder.bind(loadState, retry)
}