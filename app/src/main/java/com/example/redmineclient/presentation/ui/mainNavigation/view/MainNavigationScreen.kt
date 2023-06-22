package com.example.redmineclient.presentation.ui.mainNavigation.view

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.theme.WhiteDark
import com.example.redmineclient.presentation.ui.authentication.view.AuthenticationScreen
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorService
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModel
import com.example.redmineclient.presentation.ui.profile.view.ProfileScreen
import com.example.redmineclient.presentation.ui.projects.view.ProjectsScreen
import com.example.redmineclient.presentation.ui.tabMenu.view.TabMenuScreen
import com.example.redmineclient.presentation.ui.taskDetail.view.TaskScreen
import com.example.redmineclient.presentation.ui.timeEntries.view.TimeEntriesScreen
import com.example.redmineclient.presentation.ui.view.layouts.ErrorLayout
import com.example.redmineclient.presentation.ui.view.layouts.LoadingLayout
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


@Composable
fun MainNavigationScreen(
    startDestination: ScreenRoute,
    navController: NavHostController = rememberNavController(),
    errorService: ErrorService = get(),
    viewModelWrapper: StatefulViewModelWrapper<MainNavigationViewModel, MainNavigationState> =
        getViewModel(qualifier = named("MainNavigationViewModel")) { parametersOf(startDestination) }
) {
    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }

    val state = viewModelWrapper.state
    val backStackState = navController.currentBackStackEntryAsState()
    val currentRoute =
        backStackState.value?.destination?.route?.substringBefore("/") ?: state.value.screenRoute.name
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = currentRoute) {
        viewModelWrapper.viewModel.onRouteChange(ScreenRoute.valueOf(currentRoute))
    }

    LaunchedEffect(key1 = Unit) {
        errorService.state.collectLatest {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val doubleBackPressed = remember {
        mutableStateOf(false)
    }

    BackHandler(navController.previousBackStackEntry == null) {
        if (doubleBackPressed.value) {
            activity?.finish()
        }
        doubleBackPressed.value = true

        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        toast.show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackPressed.value = false
        }, 3000)
    }
    when (state.value.loadingState) {
        LoadingState.Loading -> {
            LoadingLayout()
        }
        LoadingState.Success -> {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = WhiteDark
            ) { innerPadding ->
                println("ROUTE: ${state.value.screenRoute.name}")
                NavHost(
                    navController = navController,
                    startDestination = state.value.screenRoute.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(
                        route = ScreenRoute.Projects.name,
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "redmineClient://${ScreenRoute.Projects.name}"
                            }
                        )
                    ) {
                        BackHandler(true) {}
                        ProjectsScreen()
                    }

                    composable(route = ScreenRoute.Authentication.name) {
                        AuthenticationScreen()
                    }

                    composable(route = ScreenRoute.Profile.name) {
                        ProfileScreen()
                    }
                    composable(
                        route = "${ScreenRoute.TabMenu.name}/{project_id}",
                        arguments = listOf(navArgument("project_id") { type = NavType.IntType })
                    ) {
                        val id = it.arguments?.getInt("project_id") ?: -1
                        print("Project_ID: $id")
                        TabMenuScreen(project_id = id)
                    }
                    composable(
                        route = "${ScreenRoute.TaskDetail.name}/{task_id}/{project_id}",
                        arguments = listOf(
                            navArgument("task_id") { type = NavType.IntType },
                            navArgument("project_id") { type = NavType.IntType}
                        )
                    ) {
                        val task_id = it.arguments?.getInt("task_id") ?: -1
                        val project_id = it.arguments?.getInt("project_id") ?: -1
                        TaskScreen(task_id = task_id, project_id = project_id)
                    }
                    composable(
                        route = "${ScreenRoute.TaskDetail.name}/{project_id}",
                        arguments = listOf(
                            navArgument("project_id") { type = NavType.IntType}
                        )
                    ) {
                        val project_id = it.arguments?.getInt("project_id") ?: -1
                        TaskScreen(project_id = project_id)
                    }
                    composable(
                        route = ScreenRoute.TimeEntriesEdit.name,
                    ) {
                        TimeEntriesScreen()
                    }
                    composable(
                        route = "${ScreenRoute.TimeEntriesEdit.name}/{time_entries_id}",
                        arguments = listOf(
                            navArgument("time_entries_id") { type = NavType.IntType }
                        )
                    ) {
                        val time_entries_id = it.arguments?.getInt("time_entries_id") ?: -1
                        TimeEntriesScreen(time_entries_id = time_entries_id)
                    }
                }
            }
        }
        LoadingState.Error -> {
            ErrorLayout {

            }
        }
    }
}

//            composable(
//                route = "${ScreenRoute.Node.name}/{id}/{type}",
//                arguments = listOf(
//                    navArgument("id") { type = NavType.IntType },
//                    navArgument("type") { type = NavType.IntType }
//                )
//            ) {
//                val id = it.arguments?.getInt("id") ?: -1
//                val type = it.arguments?.getInt("type") ?: -1
//                NodeScreen(nodeId = id, nodeType = type)
//            }
//        }
//    }
//}