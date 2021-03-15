package ru.faizovr.afisha.core.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.faizovr.afisha.core.R
import ru.faizovr.afisha.core.presentation.navigation.Command
import ru.faizovr.afisha.core.presentation.viewModel.BaseViewModel


abstract class BaseFragment<
        ScreenState : Any,
        CommandType : Command,
        ViewModel : BaseViewModel<ScreenState, CommandType>>(
    @LayoutRes val layoutResId: Int,
    viewModelClass: Class<ViewModel>
) : Fragment(layoutResId) {

    protected lateinit var navController: NavController
        private set

    protected val viewModel: ViewModel by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        subscribeToViewModelObservables()
    }

    private fun subscribeToViewModelObservables() {
        val modelObserver = Observer(this::renderView)
        viewModel.modelUpdate.observe(viewLifecycleOwner, modelObserver)
        val commandObserver = Observer(this::executeCommand)
        viewModel.commandsLiveData.observe(viewLifecycleOwner, commandObserver)
    }

    protected abstract fun renderView(model: ScreenState)

    protected open fun executeCommand(command: CommandType) {
        showUnderdevelopmentMessage()
    }

    protected fun showUnderdevelopmentMessage() {
        view?.run {
            Snackbar.make(this, R.string.text_under_development, Snackbar.LENGTH_SHORT).show()
        }
    }
}