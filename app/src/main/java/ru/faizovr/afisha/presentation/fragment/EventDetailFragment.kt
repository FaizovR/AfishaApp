package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.faizovr.afisha.R
import ru.faizovr.afisha.core.presentation.fragment.RefreshableFragment
import ru.faizovr.afisha.databinding.FragmentEventDetailBinding
import ru.faizovr.afisha.presentation.commands.EventDetailCommands
import ru.faizovr.afisha.presentation.model.EventDetailDataView
import ru.faizovr.afisha.presentation.model.EventDetailScreenState
import ru.faizovr.afisha.presentation.viewmodel.EventDetailViewModel

@AndroidEntryPoint
class EventDetailFragment :
    RefreshableFragment<EventDetailScreenState, EventDetailCommands, EventDetailViewModel>(
        R.layout.fragment_event_detail,
        EventDetailViewModel::class.java
    ) {

    private val binding by viewBinding(FragmentEventDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = EventDetailFragmentArgs.fromBundle(requireArguments())
        setupToolbar(args.title)
        viewModel.init(args.eventId)
    }

    private fun setupToolbar(title: String) =
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = title
        }

    override fun renderView(model: EventDetailScreenState) {
        super.renderView(model)
        setEventVisible(model.isEventDetailVisible)
        updateViews(model.eventDetail)
    }

    override fun executeCommand(command: EventDetailCommands) =
        when (command) {
            else -> super.executeCommand(command)
        }

    private fun updateViews(eventDetail: EventDetailDataView?) {
        eventDetail?.let {
            with(binding) {
                Glide.with(this@EventDetailFragment)
                    .load(it.imageUrl)
                    .placeholder(R.color.backgroundColor)
                    .into(imageViewDetail)
                textViewPlace.text = it.place
                textViewDate.text = it.dateString
                textViewTittleEvent.text = it.tittle
                textViewDescription.text = it.description
            }
        }
    }

    private fun setEventVisible(isVisible: Boolean) =
        with(binding) {
            imageViewDetail.isVisible = isVisible
            textViewDate.isVisible = isVisible
            textViewDescription.isVisible = isVisible
            textViewPlace.isVisible = isVisible
            textViewTittleEvent.isVisible = isVisible
        }
}