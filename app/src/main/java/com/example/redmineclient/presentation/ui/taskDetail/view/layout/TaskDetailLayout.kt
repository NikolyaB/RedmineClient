package com.example.redmineclient.presentation.ui.taskDetail.view.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.BlueLightDark
import com.example.redmineclient.presentation.theme.BlueLite
import com.example.redmineclient.presentation.theme.White
import com.example.redmineclient.presentation.ui.taskDetail.state.TaskState
import com.example.redmineclient.presentation.ui.taskDetail.view.component.ProgressBarTask
import com.example.redmineclient.presentation.ui.taskDetail.viewModel.TaskViewModel

@Composable
fun TaskDetailLayout(
    viewModelWrapper: StatefulViewModelWrapper<TaskViewModel, TaskState>,
    state: MutableState<TaskState>,
) {
    val progress = state.value.taskInfo?.done_ratio?.toDouble()?.div(100)
    val columnState: ScrollState = rememberScrollState()

    TopAppBar(
        title = {
            Text(
                text = "#${state.value.taskInfo?.id}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = BlueDark
            )
        },
        actions = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
        backgroundColor = BlueLight
    )
    Divider(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
    Box(
        modifier = Modifier

            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(BlueLight)
            .padding(top = 16.dp, bottom = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Column {
            state.value.taskInfo?.let {
                Text(
                    it.subject,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = BlueDark,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
            Row() {
                Text(
                    text = "Готовность:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = BlueLite
                )
                ProgressBarTask(
                    done_ratio = state.value.taskInfo?.done_ratio ?: 0,
                    progress = progress ?: 0.0,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(10.dp)
                        .width(170.dp),
                    textColor = BlueLightDark,
                    lineColor = BlueLightDark,
                    backgroundColor = BlueLite,
                    fontWeight = FontWeight.Black,
                    cornerRadius = 50.dp
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        items(items = state.value.taskAttributes) { item ->
            item.title?.let {
                LargeTaskItem(
                    title = it,
                    value = item.name
                )
            }
        }

        item {
            state.value.taskInfo?.let {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        Text(
                            text = "Описание",
                            color = BlueLite,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = it.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, BlueLite)
                                .padding(12.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModelWrapper.viewModel.onEditClick() },
                        colors = ButtonDefaults.buttonColors(BlueDark),
                        modifier = Modifier
                            .height(50.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = White
                            )
                            Text(
                                "Редактировать",
                                color = White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Button(
                        onClick = { viewModelWrapper.viewModel.onAddTimeEntries() },
                        colors = ButtonDefaults.buttonColors(BlueDark),
                        modifier = Modifier
                            .height(50.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreTime,
                                contentDescription = "",
                                tint = White
                            )
                            Text(
                                "Добавить трудозатраты",
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LargeTaskItem(
    title: String,
    value: String
) {
    Text(
        title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = BlueLite,
        modifier = Modifier.padding(start = 16.dp)
    )
    Box(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 8.dp)
            .fillMaxWidth()
            .background(BlueLight)
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            color = BlueDark,
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}