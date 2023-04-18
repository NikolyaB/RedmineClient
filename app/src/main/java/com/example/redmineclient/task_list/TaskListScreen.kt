package com.example.redmineclient.task_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    userId: Long,
    navController: NavController,
    viewModel: TaskListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadTasksCurrentUser()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("task_detail/-1L/${viewModel.existingUserId}")
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
                    AnimatedVisibility(visible = state.isTeamLead) {
                        IconButton(
                            onClick = { navController.navigate("user_management") },
                            modifier = Modifier
                                .height(90.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "management user"
                            )
                        }
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
                    TaskFilterDropdownMenu(
                        text = state.selectedItemMenu,
                        onTextChanged = viewModel::onFilteredTextChange,
                        onItemClick = {
                            viewModel.loadFilteredTasks(it)
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = state.tasks,
                    key = { it.id!! }
                ) { task ->
                    TaskItem(
                        task = task,
                        backgroundColor = Color(task.colorHex),
                        onTaskClick = {
                            navController.navigate("task_detail/${task.id}/${task.userId}")
                        },
                        onDeleteClick = {
                            viewModel.deleteTaskById(task.id!!)
                        },
                        onDoneClick = {
                            viewModel.updateStatusTask(it, task.id!!)
                        },
                        isDone = task.isDone,
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