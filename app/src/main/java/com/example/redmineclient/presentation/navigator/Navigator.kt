package com.example.redmineclient.presentation.navigator

import androidx.navigation.NavHostController

enum class ScreenRoute(val isMain: Boolean) {
    Authentication(false),
    Profile(false),
    Projects(true),
    TabMenu(false),
    Tasks(false),
    TaskDetail(false),
    TaskEdit(false),
    TimeEntries(false),
    TimeEntriesEdit(false)
}

interface Navigator {
    fun init(navHostController: NavHostController)

    fun navigateBack()
    fun navigateToAuthentication()
    fun navigateToProfile()
    fun navigateToProjects()
    fun navigateToTabMenu(project_id: Int)
    fun navigateToTasks()
    fun navigateToTaskDetail()
    fun navigateToTaskEdit()
    fun navigateToTimeEntries()
    fun navigateToTimeEntriesEdit()

}

class NavigatorImpl(
    startDestination: ScreenRoute,
) : Navigator {
    private var currentMainStack = startDestination
    private lateinit var navController: NavHostController

    override fun init(navHostController: NavHostController) {
        navController = navHostController
    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateToAuthentication() {
        navController.navigate(ScreenRoute.Authentication.name)
    }

    override fun navigateToProfile() {
        navController.navigate(ScreenRoute.Profile.name)
    }

    override fun navigateToProjects() {
        navController.navigate(ScreenRoute.Projects.name)
    }

    override fun navigateToTabMenu(project_id: Int) {
        navController.navigate("${ScreenRoute.TabMenu.name}/$project_id")
    }

    override fun navigateToTasks() {
        navController.navigate(ScreenRoute.Tasks.name)
    }

    override fun navigateToTaskDetail() {
        navController.navigate(ScreenRoute.TaskDetail.name)
    }

    override fun navigateToTaskEdit() {
        navController.navigate(ScreenRoute.TaskEdit.name)
    }

    override fun navigateToTimeEntries() {
        navController.navigate(ScreenRoute.TimeEntries.name)
    }

    override fun navigateToTimeEntriesEdit() {
        navController.navigate(ScreenRoute.TimeEntriesEdit.name)
    }

    private fun navigateToNavBarDestination(root: ScreenRoute) {
        navController.navigate(root.name) {
            popUpTo(navController.graph.id) {
                if (root != currentMainStack) saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }.also { currentMainStack = root }
    }

}