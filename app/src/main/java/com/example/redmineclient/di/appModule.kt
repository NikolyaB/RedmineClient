package com.example.redmineclient.di

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.data.storage.preferences.PreferencesStoreImpl
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.navigator.NavigatorImpl
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.ui.authentication.state.AuthenticationState
import com.example.redmineclient.presentation.ui.authentication.viewModel.AuthenticationViewModel
import com.example.redmineclient.presentation.ui.authentication.viewModel.AuthenticationViewModelImpl
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorService
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorServiceImpl
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModel
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModelImpl
import com.example.redmineclient.presentation.ui.profile.state.ProfileState
import com.example.redmineclient.presentation.ui.profile.viewModel.ProfileViewModel
import com.example.redmineclient.presentation.ui.profile.viewModel.ProfileViewModelImpl
import com.example.redmineclient.presentation.ui.projects.state.ProjectsState
import com.example.redmineclient.presentation.ui.projects.viewModel.ProjectsViewModel
import com.example.redmineclient.presentation.ui.projects.viewModel.ProjectsViewModelImpl
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { (start: ScreenRoute) -> NavigatorImpl(start) }
    single<ErrorService> { ErrorServiceImpl() }
    single<PreferencesStore> {
        PreferencesStoreImpl(androidContext())
    }

    viewModel(named("MainNavigationViewModel")) { (start: ScreenRoute) ->
        StatefulViewModelWrapper<MainNavigationViewModel, MainNavigationState>(
            MainNavigationViewModelImpl(start)
        )
    }

    viewModel(named("AuthenticationViewModel")) {
        StatefulViewModelWrapper<AuthenticationViewModel, AuthenticationState>(
            AuthenticationViewModelImpl(navigator = get(), userUseCase = get(), preferencesStore = get())
        )
    }

    viewModel(named("ProjectsViewModel")) {
        StatefulViewModelWrapper<ProjectsViewModel, ProjectsState>(
            ProjectsViewModelImpl(navigator = get(), projectsUseCase = get(), preferencesStore = get())
        )
    }

    viewModel(named("TabMenuViewModel")) { (project_id: Int) ->
        StatefulViewModelWrapper<TabMenuViewModel, TabMenuState>(
            TabMenuViewModelImpl(
                navigator = get(),
                preferencesStore = get(),
                taskUseCase = get(),
                timeEntriesUseCase = get(),
                project_id = project_id
            )
        )
    }

    viewModel(named("ProfileViewModel")) {
        StatefulViewModelWrapper<ProfileViewModel, ProfileState> (
            ProfileViewModelImpl(
                navigator = get(),
                userUseCase = get(),
                preferencesStore = get()
            )
        )
    }
}