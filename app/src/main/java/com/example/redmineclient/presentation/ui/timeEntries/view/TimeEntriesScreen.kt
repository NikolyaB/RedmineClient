package com.example.redmineclient.presentation.ui.timeEntries.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.ui.timeEntries.state.TimeEntriesState
import com.example.redmineclient.presentation.ui.timeEntries.view.layout.TimeEntriesLayout
import com.example.redmineclient.presentation.ui.timeEntries.viewModel.TimeEntriesViewModel
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@Composable
fun TimeEntriesScreen(
    time_entries_id: Int? = null,
    viewModelWrapper: StatefulViewModelWrapper<TimeEntriesViewModel, TimeEntriesState> = getViewModel(
        named("TimeEntriesViewModel")) { parametersOf(time_entries_id) }
) {
    val state = viewModelWrapper.state

    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.value.loadingState) {
            LoadingState.Loading -> {
                LoadingLayout()
            }

            LoadingState.Success -> {
                TimeEntriesLayout(
                    viewModelWrapper = viewModelWrapper,
                    state = state
                )
            }

            LoadingState.Error -> {
                ErrorLayout {
                    viewModelWrapper.viewModel.onRefreshClick()
                }
            }
        }
    }
}