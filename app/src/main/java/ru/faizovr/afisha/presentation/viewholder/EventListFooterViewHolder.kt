package ru.faizovr.afisha.presentation.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.EventListFooterViewHolderBinding

class EventListFooterViewHolder(
    private val binding: EventListFooterViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    // TODO: 02.03.2021 Вынести логику !isLoading
    fun bind(state: LoadState, retry: () -> Unit) {
        val isLoading = state is LoadState.Loading
        with(binding) {
            buttonFooterRetry.isVisible = !isLoading
            textViewFooterError.isVisible = !isLoading
            progressBarFooter.isVisible = isLoading
            buttonFooterRetry.setOnClickListener { retry.invoke() }
        }
    }
}