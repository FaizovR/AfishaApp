package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.FragmentEventDetailBinding
import ru.faizovr.afisha.presentation.viewmodel.EventDetailViewModel
import ru.faizovr.afisha.presentation.viewmodel.EventDetailViewModelFactory

class EventDetailFragment : Fragment(R.layout.fragment_event_detail) {

    private val viewModel: EventDetailViewModel by viewModels {
        val repository = (requireActivity().application as App).repository
        EventDetailViewModelFactory(repository, eventId)
    }
    private val eventId: Long by lazy { requireArguments().getLong(EVENT_DETAIL_ID_KEY) }
    private val binding by viewBinding(FragmentEventDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
        setupView()
    }

    private fun setupView() {
        binding.buttonEventDetailRetry.setOnClickListener {
            viewModel.onRetryClicked()
        }
    }

    private fun setupToolbar() {
        val title: String = arguments?.getString(EVENT_DETAIL_TITLE_KEY) ?: ""
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    private fun setupObservers() {
        viewModel.buttonRetryVisibility.observe(
            viewLifecycleOwner,
            this@EventDetailFragment::setRetryButtonVisibility
        )
        viewModel.eventDetailVisibility.observe(
            viewLifecycleOwner,
            this@EventDetailFragment::setEventVisible
        )
        viewModel.errorTextVisibility.observe(
            viewLifecycleOwner,
            this@EventDetailFragment::setErrorTextVisibility
        )
        viewModel.progressBarVisibility.observe(
            viewLifecycleOwner,
            this@EventDetailFragment::setProgressBarVisibility
        )
        viewModel.eventDetailInfoView.observe(viewLifecycleOwner) {
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

    private fun setEventVisible(isVisible: Boolean) {
        with(binding) {
            imageViewDetail.isVisible = isVisible
            textViewDate.isVisible = isVisible
            textViewDescription.isVisible = isVisible
            textViewPlace.isVisible = isVisible
            textViewTittleEvent.isVisible = isVisible
        }
    }

    private fun setRetryButtonVisibility(isVisible: Boolean) {
        binding.buttonEventDetailRetry.isVisible = isVisible
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBarEventDetail.isVisible = isVisible
    }

    private fun setErrorTextVisibility(isVisible: Boolean) {
        binding.textViewEventDetailFailedMessage.isVisible = isVisible
    }

    companion object {
        const val EVENT_DETAIL_ID_KEY = "ID"
        private const val EVENT_DETAIL_TITLE_KEY = "TITLE"
        fun newInstance(eventId: Long, eventTitle: String): EventDetailFragment {
            val args = Bundle()

            args.putString(EVENT_DETAIL_TITLE_KEY, eventTitle)
            args.putLong(EVENT_DETAIL_ID_KEY, eventId)
            val fragment = EventDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}