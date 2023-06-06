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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.redmineclient.presentation.navigator.ScreenRoute
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.presentation.ui.authentication.view.AuthenticationScreen
import com.example.redmineclient.presentation.ui.mainNavigation.service.ErrorService
import com.example.redmineclient.presentation.ui.mainNavigation.state.MainNavigationState
import com.example.redmineclient.presentation.ui.mainNavigation.viewModel.MainNavigationViewModel
import com.example.redmineclient.presentation.ui.profile.view.ProfileScreen
import com.example.redmineclient.presentation.ui.projects.view.ProjectsScreen
import com.example.redmineclient.presentation.ui.tabMenu.view.TabMenuScreen
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
    val state = viewModelWrapper.state
    val backStackState = navController.currentBackStackEntryAsState()
    val currentRoute =
        backStackState.value?.destination?.route?.substringBefore("/") ?: startDestination.name
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    DisposableEffect(key1 = viewModelWrapper) {
        viewModelWrapper.viewModel.onViewShown()
        onDispose { viewModelWrapper.viewModel.onViewHidden() }
    }

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

        val toast = Toast.makeText(context, "Пока", Toast.LENGTH_LONG)
        toast.show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackPressed.value = false
        }, 3000)
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination.name,
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
            composable(route = ScreenRoute.TaskDetail.name) {

            }
            composable(route = ScreenRoute.TaskEdit.name) {

            }
            composable(route = ScreenRoute.TimeEntries.name) {

            }
            composable(route = ScreenRoute.TimeEntriesEdit.name) {

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
        }
    }
}