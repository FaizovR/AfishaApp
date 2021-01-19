package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.faizovr.afisha.databinding.EventListFooterViewHolderBinding

class EventListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: EventListFooterViewHolderBinding by viewBinding(
        EventListFooterViewHolderBinding::bind
    )

    fun bind(state: LoadState, retry: () -> Unit) {
        val isLoading = state is LoadState.Loading
        with(binding) {
            buttonFooterRetry.isVisible = !isLoading
            textViewFooterError.isVisible = !isLoading
            progressBarFooter.isVisible = isLoading
            buttonFooterRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}