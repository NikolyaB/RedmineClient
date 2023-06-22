package com.example.redmineclient.presentation.ui.taskDetail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.ui.taskDetail.state.TaskState
import com.example.redmineclient.presentation.ui.taskDetail.view.layout.TaskDetailLayout
import com.example.redmineclient.presentation.ui.taskDetail.view.layout.TaskEditLayout
import com.example.redmineclient.presentation.ui.taskDetail.viewModel.TaskViewModel
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@Composable
fun TaskScreen(
    task_id: Int? = null,
    project_id: Int,
    viewModelWrapper: StatefulViewModelWrapper<TaskViewModel, TaskState> = getViewModel(
        named("TaskDetailViewModel")) { parametersOf(project_id, task_id) }
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
        if (task_id == null) {
            when (state.value.loadingState) {
                LoadingState.Loading -> {
                    LoadingLayout()
                }

                LoadingState.Success -> {
                    TaskEditLayout(
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

        } else {
            if (state.value.isEditTask) {
                when (state.value.loadingState) {
                    LoadingState.Loading -> {
                        LoadingLayout()
                    }

                    LoadingState.Success -> {
                        TaskEditLayout(
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
            } else {
                when (state.value.loadingState) {
                    LoadingState.Loading -> {
                        LoadingLayout()
                    }

                    LoadingState.Success -> {
                        TaskDetailLayout(
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
    }
}