package com.example.redmineclient.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.redmineclient.task_detail.TaskDetailScreen
import com.example.redmineclient.task_list.TaskListScreen
import com.example.redmineclient.user_management.UserManagementScreen
import com.example.redmineclient.user_sign_in.UserSignInScreen
import com.example.redmineclient.user_sign_up.UserSignUpScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "user_signIn") {
        composable(route = "user_signIn") {
            UserSignInScreen(navController = navController)
        }
        composable(route = "user_signUp") {
            UserSignUpScreen(navController = navController)
        }
        composable(route = "user_management") {
            UserManagementScreen(navController = navController)
        }
        composable(
            route = "task_list/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: -1L
            TaskListScreen(userId = userId, navController = navController)
        }
        composable(
            route = "task_detail/{taskId}/{userId}",
            arguments = listOf(
                navArgument(name = "taskId") {
                    type = NavType.LongType
                    defaultValue = -1L
                },
                navArgument(name = "userId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: -1L
            TaskDetailScreen(taskId = taskId, navController = navController)
        }

    }
}