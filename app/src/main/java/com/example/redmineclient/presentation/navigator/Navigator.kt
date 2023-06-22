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
    fun navigateToTaskDetail(task_id: Int? = null, project_id: Int)
    fun navigateToTaskEdit()
    fun navigateToTimeEntries()
    fun navigateToTimeEntriesEdit(time_entries_id: Int? = null)

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

    override fun navigateToTaskDetail(task_id: Int?, project_id: Int) {
        if (task_id == null) {
            navController.navigate("${ScreenRoute.TaskDetail.name}/$project_id")
        } else {
            navController.navigate("${ScreenRoute.TaskDetail.name}/$task_id/$project_id")
        }
    }

    override fun navigateToTaskEdit() {
        navController.navigate(ScreenRoute.TaskEdit.name)
    }

    override fun navigateToTimeEntries() {
        navController.navigate(ScreenRoute.TimeEntries.name)
    }

    override fun navigateToTimeEntriesEdit(time_entries_id: Int?) {
        if (time_entries_id == null) {
            navController.navigate(ScreenRoute.TimeEntriesEdit.name)
        } else {
            navController.navigate("${ScreenRoute.TimeEntriesEdit.name}/$time_entries_id")
        }
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