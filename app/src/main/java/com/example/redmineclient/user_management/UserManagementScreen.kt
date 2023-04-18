package com.example.redmineclient.user_management

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redmineclient.task_list.HideableSearhTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserManagementScreen(
    navController: NavController,
    viewModel: UserManagementViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadUsers()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("user_signUp")
                },
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    IconButton(
                        onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .height(90.dp)
                                .padding(horizontal = 4.dp)
                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "management user"
                        )
                    }
                    HideableSearhTextField(
                        text = state.searchText,
                        isSearchActive = state.isSearchActive,
                        onTextChange = viewModel::onSearchTextChange,
                        onSearchClick = viewModel::onToggleSearch,
                        onCloseClick = viewModel::onToggleSearch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .width(200.dp)
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "Редактирование пользователей",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = state.users,
                    key = { it.id!! }
                ) { user ->
                    UserItem(
                        user = user,
                        onUserClick = {
                            navController.navigate("task_list/${user.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteUserById(user.id!!)
                        },
                        onTeamLeadClick = {
                            viewModel.changeGradeUser(it, user.id!!)
                        },
                        isTeamLead = user.isTeamLead,
                        onAddTaskClick = { navController.navigate("task_detail/-1L/${user.id}") },
                        onShowTaskClick = { navController.navigate("task_list/${user.id}") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}