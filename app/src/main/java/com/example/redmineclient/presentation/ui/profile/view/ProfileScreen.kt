package com.example.redmineclient.presentation.ui.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.res.stringResource
import com.example.redmineclient.R
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.ui.profile.state.ProfileState
import com.example.redmineclient.presentation.ui.profile.view.layout.ProfileLayout
import com.example.redmineclient.presentation.ui.profile.viewModel.ProfileViewModel
import com.example.redmineclient.presentation.ui.tabMenu.view.layout.TimeEntriesLayout
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import org.koin.androidx.compose.getViewModel
import org.koin.core.qualifier.named

@Composable
fun ProfileScreen(
    viewModelWrapper: StatefulViewModelWrapper<ProfileViewModel, ProfileState> =
        getViewModel(named("ProfileViewModel"))

) {
    val state = viewModelWrapper.state

    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }
    Column {
        TopAppBar(
            title = { Text(text = stringResource(R.string.Profile)) },
            actions = {
                IconButton(onClick = {  }) {

                }
            }
        )

        when (state.value.loadingState) {
            LoadingState.Loading -> {
                LoadingLayout()
            }
            LoadingState.Success -> {
                ProfileLayout(state = state)
            }
            LoadingState.Error -> {
                ErrorLayout {
                    viewModelWrapper.viewModel.onCurrentUserRefresh()
                }
            }
        }
    }
}