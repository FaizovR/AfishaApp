package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.databinding.FragmentEventDetailBinding
import ru.faizovr.afisha.presentation.ScreenState
import ru.faizovr.afisha.presentation.viewmodel.EventDetailViewModel
import ru.faizovr.afisha.presentation.viewmodel.EventDetailViewModelFactory

class EventDetailFragment : Fragment(R.layout.fragment_event_detail) {

    private val viewModel: EventDetailViewModel by viewModels {
        val repository = (requireActivity().application as App).repository
        val eventId = arguments?.getLong(EVENT_DETAIL_ID_KEY) ?: 1
        EventDetailViewModelFactory(repository, eventId)
    }
    private var binding: FragmentEventDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Default -> {
                    defaultState()
                }
                ScreenState.Loading -> {
                    loadState()
                }
                ScreenState.Error -> {
                    errorState()
                }
                null -> {
                    errorState()
                }
            }
        }
        viewModel.eventDetailInfoView.observe(viewLifecycleOwner) {
            val receiver = binding
            if (receiver != null) {
                with(receiver) {
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
    }

    private fun defaultState() {
        val receiver = binding
        if (receiver != null) {
            with(receiver) {
                imageViewDetail.isVisible = true
                textViewDate.isVisible = true
                textViewDescription.isVisible = true
                textViewPlace.isVisible = true
                textViewTittleEvent.isVisible = true

                textViewEventDetailFailedMessage.isVisible = false
                buttonEventDetailRetry.isVisible = false

                progressBarEventDetail.isVisible = false
            }
        }
    }

    private fun setupToolbar() {
        val title: String = arguments?.getString(EVENT_DETAIL_TITLE_KEY) ?: ""
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    private fun loadState() {
        val binding1 = binding
        if (binding1 != null) {
            with(binding1) {
                imageViewDetail.isVisible = false
                textViewDate.isVisible = false
                textViewDescription.isVisible = false
                textViewPlace.isVisible = false
                textViewTittleEvent.isVisible = false

                textViewEventDetailFailedMessage.isVisible = false
                buttonEventDetailRetry.isVisible = false

                progressBarEventDetail.isVisible = true
            }
        }
    }

    private fun errorState() {
        val binding1 = binding
        if (binding1 != null) {
            with(binding1) {
                imageViewDetail.isVisible = false
                textViewDate.isVisible = false
                textViewDescription.isVisible = false
                textViewPlace.isVisible = false
                textViewTittleEvent.isVisible = false

                textViewEventDetailFailedMessage.isVisible = true
                buttonEventDetailRetry.isVisible = true

                progressBarEventDetail.isVisible = false
            }
        }
    }

    companion object {
        private const val EVENT_DETAIL_ID_KEY = "ID"
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