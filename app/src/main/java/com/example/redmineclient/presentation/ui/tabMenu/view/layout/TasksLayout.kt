package com.example.redmineclient.presentation.ui.tabMenu.view.layout

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.tasks.TaskInfo
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel

@Composable
fun TasksLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState>,
    state: MutableState<TabMenuState>,
) {
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier.fillMaxSize()
    ) {
        val tasks = state.value.tasks
        items(items = tasks!!) { task ->
            LargeTaskLayout(
                task,
                onClick = { viewModelWrapper.viewModel.onTaskClick(task) }
            )
        }
    }
}

@Composable
fun LargeTaskLayout(
    task: TaskInfo,
    onClick: () -> Unit,
    context: Context = LocalContext.current.applicationContext
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .clickable { Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show(); onClick() }
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            Text(task.subject)
            Text("#${task.id}")
            Text("Приоритет: ${task.priority.name.lowercase()}")
            Text("Статус: ${task.status.name.lowercase()}")
        }
    }
}