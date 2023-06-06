package com.example.redmineclient.di

import com.example.redmineclient.domain.gateway.user.UserGateway
import com.example.redmineclient.domain.usecase.project.ProjectUseCase
import com.example.redmineclient.domain.usecase.task.TaskUseCase
import com.example.redmineclient.domain.usecase.timeEntries.TimeEntriesUseCase
import com.example.redmineclient.domain.usecase.user.UserUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.navigator.NavigatorImpl
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorService
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorServiceImpl
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModel
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory<ProjectUseCase> {
        ProjectUseCase(get())
    }

    factory<TaskUseCase> {
        TaskUseCase(get())
    }

    factory<TimeEntriesUseCase> {
        TimeEntriesUseCase(get())
    }

    factory<UserUseCase> {
        UserUseCase(get())
    }
}