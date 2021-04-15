package ru.faizovr.afisha.core.presentation.extensions

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.progressindicator.BaseProgressIndicator

fun BaseProgressIndicator<*>.toggle(show: Boolean) {
    if (show) show()
    else hide()
}

fun ProgressBar.toggle(show: Boolean) {
    isVisible = show
}

fun SwipeRefreshLayout.toggle(show: Boolean) {
    isRefreshing = show
}