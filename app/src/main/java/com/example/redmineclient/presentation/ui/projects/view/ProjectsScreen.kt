package com.example.redmineclient.presentation.ui.projects.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.R
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.ui.projects.state.ProjectsState
import com.example.redmineclient.presentation.ui.projects.view.layout.ProjectLayout
import com.example.redmineclient.presentation.ui.projects.viewModel.ProjectsViewModel
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import org.koin.androidx.compose.getViewModel
import org.koin.core.qualifier.named


@Composable
fun ProjectsScreen(
//    lazyColumnState: ScrollState = rememberScrollState(),
    viewModelWrapper: StatefulViewModelWrapper<ProjectsViewModel, ProjectsState> = getViewModel(named("ProjectsViewModel"))
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
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.Projects),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueDark
                )
            },
            actions = {
                IconButton(onClick = { viewModelWrapper.viewModel.onProfileClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = stringResource(R.string.Profile_user),
                        tint = BlueDark,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            },
            backgroundColor = BlueLight,
            modifier = Modifier
                .height(66.dp)
        )

        when (state.value.loadingState) {
            LoadingState.Loading -> {
                LoadingLayout()
            }
            LoadingState.Success -> {
                ProjectLayout(
                    projects = state.value.projectResponse,
                    onProjectClick = { viewModelWrapper.viewModel.onProjectClick(it) },
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