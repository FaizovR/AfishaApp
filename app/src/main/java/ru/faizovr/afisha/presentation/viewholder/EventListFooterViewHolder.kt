package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.databinding.EventListFooterViewHolderBinding

class EventListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListFooterViewHolderBinding =
        EventListFooterViewHolderBinding.bind(itemView)


    fun bind(state: LoadState, retry: () -> Unit) {
        val isLoading = state is LoadState.Loading
        binding.buttonFooterRetry.isVisible = !isLoading
        binding.textViewFooterError.isVisible = !isLoading
        binding.progressBarFooter.isVisible = isLoading
        binding.buttonFooterRetry.setOnClickListener {
            retry.invoke()
        }
    }
}