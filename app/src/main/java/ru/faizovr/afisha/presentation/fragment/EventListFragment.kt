package ru.faizovr.afisha.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_event_list.*
import ru.faizovr.afisha.App
import ru.faizovr.afisha.R
import ru.faizovr.afisha.domain.model.Category
import ru.faizovr.afisha.presentation.contract.EventListContract
import ru.faizovr.afisha.presentation.presenter.EventListPresenter

class EventListFragment(private val category: Category) : Fragment(R.layout.fragment_event_list),
    EventListContract.View {

    private var presenter: EventListContract.Presenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPresenter()
    }

    private fun setupPresenter() {
        val app: App = activity?.application as App
        presenter = EventListPresenter(this, app.repository, category)
        presenter?.init()
    }

    override fun setText(message: String) {
        text_response.text = message
    }

}