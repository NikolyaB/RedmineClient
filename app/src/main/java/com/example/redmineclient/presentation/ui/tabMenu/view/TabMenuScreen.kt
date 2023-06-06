package com.example.redmineclient.presentation.ui.tabMenu.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuItem
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.view.components.TopTabBar
import com.example.redmineclient.presentation.ui.tabMenu.view.layout.TasksLayout
import com.example.redmineclient.presentation.ui.tabMenu.view.layout.TimeEntriesLayout
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel
import com.example.redmineclient.presentation.ui.view.layouts.EmptyLayout
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@Composable
fun TabMenuScreen(
    project_id: Int,
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState> =
        getViewModel(named("TabMenuViewModel")) { parametersOf(project_id) }
) {
    val state = viewModelWrapper.state

    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }
    val tasksLazyState = rememberLazyListState()
    val timeEntriesLazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopTabBar(currentTab = state.value.tabState) {
            val result = viewModelWrapper.viewModel.onTabClick(it)
            if (!result && it == TabMenuItem.Tasks)
                coroutineScope.launch {
                    tasksLazyState.scrollToItem(0)
                }
            else if (!result && it == TabMenuItem.TimeEntries)
                coroutineScope.launch {
                    timeEntriesLazyState.scrollToItem(0)
                }
        }
        when (state.value.tabState) {
            TabMenuItem.Tasks -> StatefulTasksLayout(
                lazyColumnState = tasksLazyState,
                viewModelWrapper = viewModelWrapper,
                state = state
            )
            TabMenuItem.TimeEntries -> StatefulTimeEntriesLayout(
                lazyColumnState = timeEntriesLazyState,
                viewModelWrapper = viewModelWrapper,
                state = state
            )
        }
    }
}

@Composable
fun StatefulTasksLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState> =
        getViewModel(named("TabMenuViewModel")),
    state: MutableState<TabMenuState>
) {
//    Refreshable(
//        isRefreshing = state.value.isMainRefreshing,
//        onRefresh = { viewModelWrapper.viewModel.onMainRefresh() }) {
        when (state.value.loadingStateTasks) {
            LoadingState.Loading -> {
                LoadingLayout()
            }
            LoadingState.Success -> {
                TasksLayout(
                    lazyColumnState = lazyColumnState,
                    viewModelWrapper = viewModelWrapper,
                    state = state
                )
            }
            LoadingState.Error -> {
                ErrorLayout {
                    viewModelWrapper.viewModel.onTasksRefresh()
                }
            }
        }
//    }
}

@Composable
fun StatefulTimeEntriesLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState> =
        getViewModel(named("TabMenuViewModel")),
    state: MutableState<TabMenuState>
) {
//    Refreshable(
//        isRefreshing = state.value.feedPageData.isRefreshing,
//        onRefresh = { viewModelWrapper.viewModel.onFeedRefresh() }) {
        when (state.value.loadingStateTimeEntries) {
            LoadingState.Loading -> {
                LoadingLayout()
            }
            LoadingState.Success -> {
                TimeEntriesLayout(
                    lazyColumnState = lazyColumnState,
                    viewModelWrapper = viewModelWrapper,
                    state = state
                )
            }
            LoadingState.Error -> {
                ErrorLayout {
                    viewModelWrapper.viewModel.onTimeEntriesRefresh()
                }
            }
        }
//    }
}
