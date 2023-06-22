package com.example.redmineclient.presentation.ui.tabMenu.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.tasks.TaskWithTimer
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.BlueLightDark
import com.example.redmineclient.presentation.theme.White
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel
import com.example.redmineclient.presentation.ui.taskDetail.view.component.ProgressBarTask

@Composable
fun TasksLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState>,
    state: MutableState<TabMenuState>,
) {

    var expanded by remember { mutableStateOf(false) }
    val filter = listOf("Новая","В работе", "Решена", "Нужен отклик", "Закрыта")
    var selectedText by remember { mutableStateOf("Фильтр") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModelWrapper.viewModel.onCreateTaskClick() },
            backgroundColor = BlueDark,
            contentColor = White
        ) {
            Icon(Icons.Filled.Add,"")
        }
    }) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 4.dp)
            ) {
                Button(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    colors = ButtonDefaults.buttonColors(White),

                ) {
                    Row {
                        Text(
                            text = selectedText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = BlueDark
                        )
                        Icon(icon, "contentDescription")
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    filter.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedText = label
                            expanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(items = state.value.tasksTime) { task ->
                    LargeTaskLayout(
                        task = task,
                        state = state,
                        onStartClick = { viewModelWrapper.viewModel.onStartClick(task) },
                        onPauseClick = { viewModelWrapper.viewModel.onPauseClick(task) },
                        onStopClick = { viewModelWrapper.viewModel.onStopClick(task) },
                        onClick = { viewModelWrapper.viewModel.onTaskClick(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun LargeTaskLayout(
    task: TaskWithTimer,
    state: MutableState<TabMenuState>,
    onClick: () -> Unit,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            ),
        backgroundColor = Blue
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        ) {
            Box (
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .background(BlueLight)
            ) {
                Text(
                    text = task.subject,
                    fontSize = 18.sp,
                    color = BlueDark,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )

                Text(
                    task.assigned_to.name,
                    color = White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
                Text(
                    "#${task.id}",
                    color = White
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 16.dp)
            ) {
               Text(
                   task.tracker.name,
                   color = White
               )
               Text(
                   task.status.name,
                   color = White,
                   modifier = Modifier
                       .padding(horizontal = 8.dp)
               )
               Text(
                   task.priority.name,
                   color = White
               )
            }

            val progress = task.done_ratio.toDouble().div(100)
            ProgressBarTask(
                done_ratio = task.done_ratio,
                progress = progress,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(10.dp)
                    .width(170.dp),
                textColor = BlueLightDark,
                lineColor = BlueLightDark,
                backgroundColor = BlueLight,
                fontWeight = FontWeight.Black,
                cornerRadius = 50.dp
            )

            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.value.isPlaying) {
                    IconButton(
                        onClick = onPauseClick,
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PauseCircle,
                            contentDescription = "",
                            tint = White
                        )
                    }
                } else {
                    IconButton(
                        onClick = onStartClick,
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayCircle,
                            contentDescription = "",
                            tint = White
                        )
                    }
                }
                IconButton(
                    onClick = { onStopClick() },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.StopCircle,
                        contentDescription = "",
                        tint = White
                    )
                }

                Row {
                    Text(
                        text = "${state.value.hours}:${state.value.minutes}:${state.value.seconds}",
                        fontSize = 16.sp,
                        color = White
                    )
//                    Text(
//                        text = ":",
//                        fontSize = 16.sp,
//                        color = White
//                    )
//                    Text(
//                        text = task.minutes,
//                        fontSize = 16.sp,
//                        color = White
//                    )
//                    Text(
//                        text = ":",
//                        fontSize = 16.sp,
//                        color = White
//                    )
//                    Text(
//                        text = task.seconds,
//                        fontSize = 16.sp,
//                        color = White
//                    )
                }
            }
        }
    }
}