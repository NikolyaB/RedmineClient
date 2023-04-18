package com.example.redmineclient.task_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redmineclient.common_ui.TransparentHintTextField
import com.example.redmineclient.task_list.TaskPriorityDropdownMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskDetailScreen(
    taskId: Long,
    navController: NavController,
    viewModel: TaskDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasTaskBeenSaved by viewModel.hasTaskBeenSaved.collectAsState()
    LaunchedEffect(key1 = hasTaskBeenSaved) {
        if (hasTaskBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveTask,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save task",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.taskColor))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TaskPriorityDropdownMenu(
                itemLabel = state.taskPriority,
                onValueChanged = viewModel::onTaskPriorityChanged
            )
            Spacer(modifier = Modifier.height(12.dp))
            TransparentHintTextField(
                text = state.taskTitle,
                hint = "Заголовок",
                isHintVisible = state.isTaskTitleHintVisible,
                onValueChanged = viewModel::onTaskTitleChanged,
                onFocusChanged = {
                    viewModel.onTaskTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TransparentHintTextField(
                text = state.taskContent,
                hint = "Контент...",
                isHintVisible = state.isTaskContentHintVisible,
                onValueChanged = viewModel::onTaskContentChanged,
                onFocusChanged = {
                    viewModel.onTaskContentFocusChanged(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}