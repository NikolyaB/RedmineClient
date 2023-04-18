package com.example.redmineclient.di

import com.example.redmineclient.main.MainViewModel
import com.example.redmineclient.task_detail.TaskDetailViewModel
import com.example.redmineclient.task_list.TaskListViewModel
import com.example.redmineclient.user_management.UserManagementViewModel
import com.example.redmineclient.user_sign_in.UserSignInViewModel
import com.example.redmineclient.user_sign_up.UserSignUpViewModel
import com.example.redmineclient.data.local.DatabaseDriverFactory
import com.example.redmineclient.data.task.SqlDelightTaskDataSourceImpl
import com.example.redmineclient.data.user.SqlDelightUserDataSourceImpl
import com.example.redmineclient.database.TaskManagerDatabase
import com.example.redmineclient.domain.task.TaskDataSource
import com.example.redmineclient.domain.user.UserDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TaskListViewModel(userDataSource = get(), taskDataSource = get(), savedStateHandle = get()) }

    viewModel { TaskDetailViewModel(taskDataSource = get(), savedStateHandle = get()) }

    viewModel { UserSignUpViewModel(userDataSource = get(), savedStateHandle = get()) }

    viewModel { UserSignInViewModel(userDataSource = get(), savedStateHandle = get()) }

    viewModel { UserManagementViewModel(userDataSource = get(), savedStateHandle = get()) }

    viewModel { MainViewModel(userDataSource = get(), savedStateHandle = get()) }


    single <TaskDataSource> {
        SqlDelightTaskDataSourceImpl(db = get())
    }

    single <UserDataSource> {
        SqlDelightUserDataSourceImpl(db = get())
    }

    single {
        TaskManagerDatabase(get())
    }

    factory {
        DatabaseDriverFactory(get()).createDriver()
    }

    single {
        SqlDelightTaskDataSourceImpl(TaskManagerDatabase(driver = get()))
    }
}