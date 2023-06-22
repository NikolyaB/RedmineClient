package com.example.redmineclient.presentation.ui.timeEntries.view.layout

import android.widget.NumberPicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.tasks.TaskAttribute
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.White
import com.example.redmineclient.presentation.ui.timeEntries.state.TimeEntriesState
import com.example.redmineclient.presentation.ui.timeEntries.viewModel.TimeEntriesViewModel
import com.example.redmineclient.presentation.ui.view.layouts.component.ButtonDropDownMenu
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun TimeEntriesLayout(
    viewModelWrapper: StatefulViewModelWrapper<TimeEntriesViewModel, TimeEntriesState>,
    state: MutableState<TimeEntriesState>
) {
    var numberTask by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    val dialogDateState = rememberMaterialDialogState()
    var dialogTimeState by remember { mutableStateOf(false) }
    var hours by remember { mutableStateOf("00") }
    var minutes by remember { mutableStateOf("00") }

    val activity = listOf(
        TaskAttribute(8, name = "Проектирование"),
        TaskAttribute(9, name = "Разработа"),
        TaskAttribute(10, name = "Дизайн"),
        TaskAttribute(11, name = "Расследование"),
        TaskAttribute(12, name = "Обсуждение")
    )

    TopAppBar(
        title = {
            Text(
                text = "Трудозатраты",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = BlueDark
            )
        },
        actions = {

        },
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
        backgroundColor = BlueLight
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = "#${state.value.numberTask}",
            onValueChange = { value ->
                if (value.length <= 5) {
                    viewModelWrapper.viewModel.onNumberValueChange(value.filter { it.isDigit() })
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Номер") },
            singleLine = false,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { dialogDateState.show() },
                colors = ButtonDefaults.buttonColors(BlueLight),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "",
                        tint = BlueDark
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        "${state.value.date.year}" +
                                "-${state.value.date.monthValue}" +
                                "-${state.value.date.dayOfMonth}",
                        color = BlueDark
                    )
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Button(
                onClick = { dialogTimeState = true },
                colors = ButtonDefaults.buttonColors(BlueLight),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = "",
                        tint = BlueDark
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        if (state.value.minutes != 0 || state.value.hours != 0) {
                            "${state.value.hours}:${state.value.minutes}"
                        } else {
                            "Выбрать время"
                        },
                        color = BlueDark
                    )
                }
            }
        }
        ButtonDropDownMenu(
            title = "Деятельность",
            items = activity,
            color = White,
            backgroundColor = Blue
        ) {
            viewModelWrapper.viewModel.onActivityClick(it)
        }

        OutlinedTextField(
            value = state.value.comments,
            onValueChange = { viewModelWrapper.viewModel.onValueChangeComment(it) },
            label = { Text("Комментарий") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { viewModelWrapper.viewModel.onSaveClick() },
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
        MaterialDialog(
            dialogState = dialogDateState,
            buttons = {
                positiveButton("Ок")
                negativeButton("Отмена")
            }
        ) {
            datepicker { date ->
                viewModelWrapper.viewModel.chooseDate(date)
            }
        }
        if (dialogTimeState) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .width(200.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .weight(1f),
                            factory = { context ->
                                NumberPicker(context).apply {
                                    setOnValueChangedListener { numberPicker, i, i2 ->
                                        viewModelWrapper.viewModel.chooseHours(i2)
                                    }
                                    minValue = 0
                                    maxValue = 20
                                }
                            }
                        )
                        AndroidView(
                            modifier = Modifier
                                .weight(1f),
                            factory = { context ->
                                NumberPicker(context).apply {
                                    setOnValueChangedListener { numberPicker, i, i2 ->
                                        viewModelWrapper.viewModel.chooseMinutes(i2)
                                    }
                                    minValue = 0
                                    maxValue = 59
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = "Отмена",
                            modifier = Modifier
                                .clickable {
                                    viewModelWrapper.viewModel.chooseMinutes(0)
                                    viewModelWrapper.viewModel.chooseHours(0)
                                    dialogTimeState = false
                                }
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = "Ок",
                            modifier = Modifier
                                .clickable {
                                    viewModelWrapper.viewModel
                                        .setTotalHours("${state.value.hours}.${state.value.minutes}".toDouble())
                                    dialogTimeState = false
                                }
                        )
                    }
                }
            }
        }
    }
}