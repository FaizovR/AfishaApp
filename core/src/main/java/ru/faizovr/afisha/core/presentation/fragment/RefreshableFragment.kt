package ru.faizovr.afisha.core.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.faizovr.afisha.core.R
import ru.faizovr.afisha.core.presentation.extensions.toggle
import ru.faizovr.afisha.core.presentation.models.RefreshableScreenState
import ru.faizovr.afisha.core.presentation.navigation.Command
import ru.faizovr.afisha.core.presentation.viewModel.ScreenDataFetchingViewModel

// TODO: 02.03.2021 ADD Binding to Base Fragment
abstract class RefreshableFragment<
        ScreenState : RefreshableScreenState<*>,
        CommandType : Command,
        ViewModel : ScreenDataFetchingViewModel<*, ScreenState, CommandType>>(
    @LayoutRes layoutResId: Int,
    viewModelClass: Class<ViewModel>
) : BaseFragment<ScreenState, CommandType, ViewModel>(layoutResId, viewModelClass) {

    private var layoutPullToRefresh: SwipeRefreshLayout? = null
    private var layoutErrorLoadingData: View? = null
    private var btnRetry: Button? = null
    private var tvErrorMessage: TextView? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefreshableView(view)
    }

    private fun initRefreshableView(view: View) {
        layoutPullToRefresh = view.findViewById(R.id.layout_pull_to_refresh)
            ?: throw IllegalStateException("Wrong layout: no view with id = ${R.id.layout_pull_to_refresh}")
        layoutErrorLoadingData = view.findViewById(R.id.layoutErrorLoadingData)
            ?: throw IllegalStateException("Wrong layout: no view with id = ${R.id.layoutErrorLoadingData}")
        btnRetry = layoutErrorLoadingData?.findViewById(R.id.btnRetry)
        tvErrorMessage = layoutErrorLoadingData?.findViewById(R.id.tvErrorMessage)
        initListeners()
    }

    private fun initListeners() {
        btnRetry?.setOnClickListener {
            viewModel.onRetryButtonClicked()
        }
        layoutPullToRefresh?.setOnRefreshListener {
            viewModel.onPullToRefresh()
        }
    }

    @CallSuper
    override fun renderView(model: ScreenState) {
        updateLayoutPullToRefresh(model.isInLoadingState())
        updateErrorState(model)
    }

    private fun updateLayoutPullToRefresh(isLoading: Boolean) {
        layoutPullToRefresh?.toggle(isLoading)
    }

    private fun updateErrorState(model: ScreenState) =
        when (model.isInLoadingState()) {
            true -> hideErrorPanel()
            false -> showErrorControlsIfRequired(model.isInErrorState())
        }

    private fun hideErrorPanel() {
        layoutErrorLoadingData?.isVisible = false
    }

    private fun showErrorControlsIfRequired(isInErrorState: Boolean) {
        layoutErrorLoadingData?.isVisible = isInErrorState
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        layoutErrorLoadingData = null
        layoutPullToRefresh = null
        tvErrorMessage = null
        btnRetry = null
    }
}