package com.example.redmineclient.presentation.ui.taskDetail.view.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.tasks.TaskAttribute
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.BlueLightDark
import com.example.redmineclient.presentation.theme.BlueLite
import com.example.redmineclient.presentation.theme.White
import com.example.redmineclient.presentation.ui.taskDetail.state.TaskState
import com.example.redmineclient.presentation.ui.taskDetail.view.component.ProgressBarTask
import com.example.redmineclient.presentation.ui.taskDetail.viewModel.TaskViewModel
import com.example.redmineclient.presentation.ui.view.layouts.component.ButtonDropDownMenu
import com.example.redmineclient.presentation.ui.view.layouts.component.ButtonDropDownMenuProgress

@Composable
fun TaskEditLayout(
    viewModelWrapper: StatefulViewModelWrapper<TaskViewModel, TaskState>,
    state: MutableState<TaskState>
) {
    val columnState: ScrollState = rememberScrollState()

    val trackers = listOf(
        TaskAttribute(1, name = "Ошибка"),
        TaskAttribute(2, name = "Улучшение"),
        TaskAttribute(3, name = "Поддержка"),
        TaskAttribute(4, name = "Эпик")
    )
    val status = listOf(
        TaskAttribute(1, name = "Новая"),
        TaskAttribute(2, name = "В работе"),
        TaskAttribute(3, name = "Решена"),
        TaskAttribute(4, name = "Нужен отклик"),
        TaskAttribute(5, name = "Закрыта"),
        TaskAttribute(6, name = "Отклонена"),
    )
    val priority = listOf(
        TaskAttribute(1, name = "Низкий"),
        TaskAttribute(2, name = "Нормальный"),
        TaskAttribute(3, name = "Высокий"),
        TaskAttribute(4, name = "Срочный"),
        TaskAttribute(5, name = "Немедленный")
    )

    val doneRatios = listOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100)

    TopAppBar(
        title = {
            if (state.value.taskInfo?.id != null) {
                Text(
                    text = "#${state.value.taskInfo?.id}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = BlueDark
                )
            } else {
                Text(
                    text = "Новая задача",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = BlueDark
                )
            }
        },
        actions = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
        backgroundColor = BlueLight
    )

    Column(
        modifier = Modifier
            .verticalScroll(columnState)
            .padding(16.dp)
            .fillMaxSize()
    ) {

        OutlinedTextField(
            value = state.value.subject,
            onValueChange = { viewModelWrapper.viewModel.onSubjectChange(it) },
            label = { Text("Тема") },
            isError = state.value.subject.isEmpty(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        ButtonDropDownMenu(
            title = state.value.taskInfo?.tracker?.name ?: "Ошибка",
            items = trackers,
            backgroundColor = BlueLight,
            color = BlueDark
        ) {
            viewModelWrapper.viewModel.onTrackerClick(it)
        }

        ButtonDropDownMenu(
            title = state.value.taskInfo?.priority?.name ?: "Нормальный",
            items = priority,
            backgroundColor = Blue,
            color = White
        ) {
            viewModelWrapper.viewModel.onPriorityClick(it)
        }

        ButtonDropDownMenu(
            title = state.value.taskInfo?.status?.name ?: "Новая",
            items = status,
            backgroundColor = BlueLight,
            color = BlueDark
        ) {
            viewModelWrapper.viewModel.onStatusClick(it)
        }

        ButtonDropDownMenuProgress(
            title = "Готовность",
            items = doneRatios,
            backgroundColor = Blue,
            color = White
        ) {
            viewModelWrapper.viewModel.onDoneRatioClick(it)
        }

        ProgressBarTask(
            done_ratio = state.value.done_ratio,
            progress = state.value.done_ratio.toDouble().div(100),
            modifier = Modifier
                .height(10.dp)
                .width(100.dp),
            textColor = BlueLightDark,
            lineColor = BlueLightDark,
            backgroundColor = BlueLite,
            fontWeight = FontWeight.Black,
            cornerRadius = 50.dp
        )

        OutlinedTextField(
            value = state.value.description,
            onValueChange = { viewModelWrapper.viewModel.onDescriptionChange(it) },
            label = { Text("Описание") },
            singleLine = false,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    viewModelWrapper.viewModel.onSaveClick()
                },
                colors = ButtonDefaults.buttonColors(BlueDark),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(
                    "Сохранить",
                    color = White
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Button(
                onClick = { viewModelWrapper.viewModel.onCancelClick() },
                colors = ButtonDefaults.buttonColors(BlueDark),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(
                    "Отмена",
                    color = White
                )
            }
        }
    }
}