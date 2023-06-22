package com.example.redmineclient.di

import com.example.redmineclient.data.gateway.project.ProjectGatewayImpl
import com.example.redmineclient.data.gateway.task.TaskGatewayImpl
import com.example.redmineclient.data.gateway.timeEntries.TimeEntriesGatewayImpl
import com.example.redmineclient.data.gateway.user.UserGatewayImpl
import com.example.redmineclient.data.storage.network.project.ProjectService
import com.example.redmineclient.data.storage.network.project.ProjectServiceImpl
import com.example.redmineclient.data.storage.network.task.TaskService
import com.example.redmineclient.data.storage.network.task.TaskServiceImpl
import com.example.redmineclient.data.storage.network.timeEntries.TimeEntriesService
import com.example.redmineclient.data.storage.network.timeEntries.TimeEntriesServiceImpl
import com.example.redmineclient.data.storage.network.user.UserService
import com.example.redmineclient.data.storage.network.user.UserServiceImpl
import com.example.redmineclient.domain.gateway.project.ProjectGateway
import com.example.redmineclient.domain.gateway.task.TaskGateway
import com.example.redmineclient.domain.gateway.timeEntries.TimeEntriesGateway
import com.example.redmineclient.domain.gateway.user.UserGateway
import org.koin.dsl.module

val dataModule = module {

    single<UserService> {
        UserServiceImpl(get())
    }

    single {
        UserServiceImpl(get())
    }

    single<ProjectService> {
        ProjectServiceImpl(get())
    }

    single {
        ProjectServiceImpl(get())
    }

    single<TaskService> {
        TaskServiceImpl(get())
    }

    single {
        TaskServiceImpl(get())
    }

    single<TimeEntriesService> {
        TimeEntriesServiceImpl(get())
    }

    single {
        TimeEntriesServiceImpl(get())
    }

    single<TimeEntriesGateway> {
        TimeEntriesGatewayImpl(get())
    }

    single<TaskGateway> {
        TaskGatewayImpl(get())
    }

    single<ProjectGateway> {
        ProjectGatewayImpl(get())
    }

    single<UserGateway> {
        UserGatewayImpl(get())
    }
}